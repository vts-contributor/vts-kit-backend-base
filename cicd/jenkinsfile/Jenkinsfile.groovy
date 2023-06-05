@Library('jenkins-shared-lib')_
import groovy.transform.Field


@Field def useImage = true
@Field def useArtifact = false
@Field def isBackend = true

// ------------------------------------ VTS KIT ----------------------------------------

def initVTSKit() {
    // Load local environment
    try {
        echo 'Load local environment: Loading'
        load 'cicd/jenkinsfile/environment.groovy'
        echo 'Load local environment: Success'

        // Load remote environment
        if (env.remoteConfigFile != null && env.remoteConfigFile != '') {
            try {
                echo 'Load remote environment: Loading'
                configFileProvider([configFile(fileId: "${env.remoteConfigFile}", targetLocation: 'cicd/')]) {
                    load "cicd/${env.remoteConfigFile}"
                    echo 'Load remote environment: Loaded'
                }
            } catch (IOException e) {
                echo 'Load remote environment: Error'
            }
            catch (FileNotFoundException  e) {
                echo 'Load remote environment: Error'
            }

        }
    } catch (IOException e) {
        echo 'Load local environment: Error'
    }catch (FileNotFoundException  e) {
        echo 'Load remote environment: Error'
    }

    // Extra project environments
    env.gitlabUrl = sh(script: "echo ${env.gitlabSourceRepoHttpUrl} | cut -d/ -f1-3", returnStdout:true).trim()

    def project = getProject()
    env.gitProjectApiUrl= "${env.gitlabUrl}/api/v4/projects/${project.id}"

    // Compability fix
    env.GITLAB_TOKEN_CREDENTIALS = env.gitTokenSecret
    env.GITLAB_PROJECT_API_URL = env.gitProjectApiUrl
    env.MAXIMUM_ALLOWED_BUGS = env.maximumAllowedBugs
    env.MAXIMUM_ALLOWED_VUNERABILITIES = env.maximumAllowedVunerabilities
    env.MAXIMUM_ALLOWED_CODE_SMELL = env.maximumAllowedCodeSmell
    env.PUSH_BUILD_PREFIX = env.pushBuildPrefix
    env.MR_BUILD_PREFIX = env.mrBuildPrefix
    env.ACCEPT_CLOSE_MR_BUILD_PREFIX = env.acceptCloseMRBuildPrefix

    // Dump environments //
    echo 'Environments:'
    sh 'env'
}

def getProject() {
    def current = ""
    withCredentials([string(credentialsId: "${env.gitTokenSecret}", variable: 'token')]) {
        def response = httpRequest([
                acceptType: 'APPLICATION_JSON',
                httpMode: 'GET',
                contentType: 'APPLICATION_JSON',
                customHeaders: [
                        [name: 'Private-Token', value: token]
                ],
                url: "${env.gitlabUrl}/api/v4/projects?search=${env.gitlabSourceRepoName}"
        ])
        def projects = jenkinsfile_utils.jsonParse(response.content)
        for (project in projects) {
            if (project['web_url'] == env.gitlabSourceRepoHomepage) {
                current = project
                return
            }
        }
        return
    }
    if (current == "") {
        error "Unable to find project from gitlab API"
    } else {
        return current
    }
}


// ------------------------------------ PRE DEFINED -------------------------------------
// WARNING! DO NOT MODIFY NAME/PARAMS OF ORIGINAL FUNCTIONS

def getServiceList(){
    def listService = []
    return listService
}

def buildService() {
    stage('Build service') {
        env.buildWorkspace = sh(returnStdout: true, script: 'pwd').trim()
        sh """
            sh cicd/scripts/dev-build-script.sh
        """
    }
}

def unitTestAndCodeCoverage(buildType){
    stage("Checkout source code"){
        jenkinsfile_utils.checkoutSourceCode(buildType)
    }
    stage("Unit Test & Code Coverage") {
//        try {
//            sh """
//            mvn test org.jacoco:jacoco-maven-plugin:0.8.5:report-aggregate -Dmaven.test.failure.ignore=true
//            """
//            echo "code coverage done"
//            jacoco([
//                classPattern : 'target/classes',
//                sourcePattern: 'src/main/java'
//            ])
//            def coverageResultStrComment = "<b>Coverage Test Result:</b> <br/><br/>"
//            def coverageInfoXmlStr = readFile "target/jacoco-aggregate-report/jacoco.xml"
//            echo "Coverage Info: ${jenkinsfile_CI.getProjectCodeCoverageInfo(coverageInfoXmlStr)} "
//            coverageResultStrComment += jenkinsfile_CI.getProjectCodeCoverageInfo(coverageInfoXmlStr)
//            coverageResultStrComment += "<i><a href='${env.BUILD_URL}Code-Coverage-Report/jacoco'>" +
//                "Details Code Coverage Test Report...</a></i><br/><br/>"
//            env.CODE_COVERAGE_RESULT_STR = coverageResultStrComment
//        } catch (err) {
//            echo "Error when test Unit Test"
//            throw err
//        } finally {
//            sh 'ls -al'
//            //junit '*/target/*-results/test/TEST-*.xml'
//            junit 'target/surefire-reports/TEST-*.xml'
//            def unitTestResult = jenkinsfile_CI.getTestResultFromJenkins()
//
//            env.UNIT_TEST_PASSED = unitTestResult["passed"]
//            env.UNIT_TEST_FAILED = unitTestResult["failed"]
//            env.UNIT_TEST_SKIPPED = unitTestResult["skipped"]
//            env.UNIT_TEST_TOTAL = unitTestResult["total"]
//
//            def testResultContent = "- Passed: <b>${unitTestResult['passed']}</b> <br/>" +
//                "- Failed: <b>${unitTestResult['failed']}</b> <br/>" +
//                "- Skipped: <b>${unitTestResult['skipped']}</b> <br/>"
//
//            def testResultString = "<b> Unit Test Result:</b> <br/><br/>${testResultContent} " +
//                "<i><a href='${env.BUILD_URL}testReport/'>Details Unit Test Report...</a></i><br/><br/>"
//            env.UNIT_TEST_RESULT_STR = testResultString
//
//            if (unitTestResult['failed'] > 0) {
//                error "Failed ${unitTestResult['failed']} unit tests"
//                env.UNIT_TEST_RESULT_STR += "Failed ${unitTestResult['failed']} unit tests"
//            }
//        }
    }

}

def deployDevTest(version) {
    echo "Deploy to development server"
    echo "Version to deploy: $version"
    env.imageName = "${env.imageRegistry}/${env.harborProject}/${env.appName}:${version}"
    withCredentials([file(credentialsId: "${env.devKubeConfigFileSecret}", variable: 'kubeconfig')]){
        sh """
			sh cicd/scripts/dev-deploy-script.sh $kubeconfig
		"""
    }
}

def deployProduct(service,version) {
    echo "Deploy to production server"
    echo "Version to deploy: $version"
    env.imageName = "${env.imageRegistry}/${env.harborProject}/${env.appName}:${version}"
    withCredentials([file(credentialsId: "${env.prodKubeConfigFileSecret}", variable: 'kubeconfig')]){
        sh """
			sh cicd/scripts/prod-deploy-script.sh $kubeconfig
		"""
    }
}


def fortifyScan(){
    jenkinsfile_utils.fortifyScanStage(
            [
                    serviceName : "${env.appName}",
                    sourcePathRegex : '\\src\\main\\java\\*'
            ]
    )
}


def pushImage(){
    jenkinsfile_utils.pushImageToHarbor(
            [
                    repo_name : "${env.harborProject}",
                    image_name : "${env.appName}"
            ]
    )
}

def pushArtifact(){
    jenkinsfile_utils.pushArtifactToNexus(
            [
                    groupID     : "com.viettel",
                    artifactId  : "process",
                    filepath    : "/"
            ]
    )
}

def selfCheckService() {
    echo "Not implemented selfCheckService()"
    return true
}

def rollback() {
    echo "Not implemented rollback()"
    return true
}

def autotestProduct() {
    echo "Not implemented autotestProduct()"
    return true
}




// ------------------------------------ Self-defined functions ------------------------------------
//def checkoutDeployment(){
//    checkout changelog: true, poll: true, scm: [
//        $class                           :  'GitSCM',
//        branches                         : [[name: "master"]],
//        doGenerateSubmoduleConfigurations: false,
//        extensions                       : [[$class: 'UserIdentity',
//                                            email : 'vtsjenkinsadmin@viettel.com.vn', name: 'cicdBot'],
//                                            [$class: 'CleanBeforeCheckout']],
//        submoduleCfg                     : [],
//        userRemoteConfigs                : [[credentialsId: "${env.gitUserPassSecret}",
//                                             name         : 'origin',
//                                             url          : "${env.gitlabSourceRepoHomepage}" + ".git"]]
//    ]
//}

return this
