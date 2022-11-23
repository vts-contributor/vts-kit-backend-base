def deployToProduction() {
    def gitlabBranch = env.gitlabBranch
    echo "Branch : ${gitlabBranch}"
    def semantic_version = gitlabBranch.split("/")[2]
    env.config_git_branch = semantic_version
    echo "Config git branch: ${env.config_git_branch}"
    env.DEPLOY_RESULT_DESCRIPTION += "<h4>Test & Verify Phase Result</h4>"
    updateGitlabCommitStatus name: "deploy to production ", state: 'running'
    stage("1. Checkout Source Code") {
        jenkinsfile_utils.checkoutSourceCode("PUSH")
        def commitIdStdOut = sh(script: 'git rev-parse HEAD', returnStdout: true)
        env.DEPLOY_GIT_COMMIT_ID = commitIdStdOut.trim()
    }
    env.DEPLOY_RESULT_DESCRIPTION += "<h4>Deploy Phase Result</h4>"
    updateGitlabCommitStatus name: "deploy to production ", state: 'running'
    def deployInput = ""
    def deployer = ""
    stage("2. Wait for maintainer accept or reject to deploy to production") {
        try {
            deployer = env.project_maintainer_list
            echo "project_maintainer_list: ${env.project_maintainer_list}"

            timeout(time: 24, unit: 'HOURS') {
                deployInput = input(
                        submitter: "${deployer}",
                        submitterParameter: 'submitter',
                        message: 'Pause for wait maintainer selection', ok: "Deploy", parameters: [
                        string(defaultValue: '',
                                description: 'Version to Deploy',
                                name: 'Deploy')
                ])
            }
        } catch (err) { // timeout reached or input false
            echo "Exception"
            def user = err.getCauses()[0].getUser()
            if ('SYSTEM' == user.toString()) { // SYSTEM means timeout.
                echo "Timeout is exceeded!"
            } else {
                echo "Aborted by: [${user}]"
            }
            deployInput = "Abort"
        }
        echo "Input value: $deployInput"
    }
    // env.GIT_TAG_DEPLOY = deployInput.Deploy
    // echo "Deploy Version: ${env.GIT_TAG_DEPLOY}"
    env.GIT_TAG_DEPLOY = deployInput['Deploy']
    echo "Deploy Version: $env.GIT_TAG_DEPLOY"
    // env.project_version = "${env.GIT_TAG_DEPLOY}_GitTag_${env.config_git_branch}"
    env.project_version = "${env.GIT_TAG_DEPLOY}"
    if(deployer.contains(deployInput.submitter)){
        stage('3. Deploy to Production'){
            echo "Deploying Version: ${env.project_version}"
            sh """
		        sh cicd/prod-deploy-script.sh ${env.project_version}
               """
        }
        currentBuild.result = "SUCCESS"
    }else {
        stage("3.Cancel deploy process") {
            echo "Version: ${env.project_version}"
            echo "Deploy process is canceled."
            currentBuild.result = "ABORTED"
        }
    }
}
def rollBackTag() {
    def versionRollBack = ''
    env.DEPLOY_RESULT_DESCRIPTION += "<h4>Test & Verify Phase Result</h4>"
    stage('Wait for user submit Version to rollback') {
        try {
            timeout(time: 24, unit: 'HOURS') {
                versionRollBack = input(
                        submitter: "${env.ROLLBACK_MAINTAINER_LIST}",
                        message: 'Pause for wait maintainer selection', ok: "Rollback", parameters: [
                        string(defaultValue: '',
                                description: 'Version to rollback',
                                name: 'Version')
                ])
            }
        } catch (err) {
            echo "Exception"
            def user = err.getCauses()[0].getUser()
            if ('SYSTEM' == user.toString()) { // SYSTEM means timeout.
                echo "Timeout is exceeded!"
            } else {
                echo "Aborted by: [${user}]"
            }
            versionRollBack = 'Aborted'
        }
    }
    // def statusCode = sh(script: "git show-ref --verify refs/tags/${gitTag}", returnStatus: true)
    env.VERSION_ROLLBACK = versionRollBack
    echo "Version: $versionRollBack"
    if (versionRollBack == 'Aborted' || versionRollBack == '') {
        stage("Cancel deploy process") {
            echo "Version: $versionRollBack"
            echo "Deploy process is canceled."
            currentBuild.result = "ABORTED"
        }
    } else {
        stage("Rollback in production with version build number $versionRollBack"){
            echo "Build version rollback $versionRollBack"
            node('slave_43'){
                echo "Rollback with version $versionRollBack"
            }
        }
        currentBuild.result = "SUCCESS"
    }
}
def createIssueAndMentionMaintainer(issueTitle, issueDescription) {
    echo "issueTitle: ${issueTitle}"
    echo "issueDescription: ${issueDescription}"
    withCredentials([usernamePassword(credentialsId: 'a5eedd9f-332d-4575-9756-c358bbd808eb', usernameVariable: 'user',
            passwordVariable: 'password')]){
        def issueContentJson = """
                                    {
                                        "title": "${issueTitle}",
                                        "description": "${issueDescription}",
                                        "labels": "Deploy Result"
                                    }
                                """
        echo "issueContentJson: ${issueContentJson}"
        def createIssueResp = httpRequest([
                acceptType   : 'APPLICATION_JSON',
                httpMode     : 'POST',
                contentType  : 'APPLICATION_JSON',
                customHeaders: [[name: "PRIVATE-TOKEN", value: password ]],
                url          : "${env.GITLAB_PROJECT_API_URL}/issues",
                requestBody  : issueContentJson

        ])
        def notifyMemberLevel = 40
        def projectMemberList = jenkinsfile_utils.getProjectMember(notifyMemberLevel)
        def issueCommentStr = ""
        for (member in projectMemberList) {
            issueCommentStr += "@${member} "
        }
        def issueCreated = jenkinsfile_utils.jsonParse(createIssueResp.content)
        def issueCommentJson = """
                                    {
                                        "body": "${issueCommentStr}"
                                    }
                                """
        httpRequest([
                acceptType   : 'APPLICATION_JSON',
                httpMode     : 'POST',
                contentType  : 'APPLICATION_JSON',
                customHeaders: [[name: "PRIVATE-TOKEN", value: password ]],
                url          : "${env.GITLAB_PROJECT_API_URL}/issues/${issueCreated["iid"]}/notes",
                requestBody  : issueCommentJson
        ])
    }
}

def toList(value) {
    return [value].flatten().findAll { it != null }
}
return [
        buildPushCommit      : this.&buildPushCommit,
        buildMergeRequest    : this.&buildMergeRequest,
        deployToProduction: this.&deployToProduction,
        rollBackTag       : this.&rollBackTag
]
