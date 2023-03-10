@Library('jenkins-shared-lib')_
import groovy.transform.Field


@Field def useImage = true
@Field def useArtifact = false
@Field def isBackend = true


load 'cicd/jenkinsfile/environment.groovy'

// WARNING! DO NOT MODIFY NAME/PARAMS OF ORIGINAL FUNCTIONS
def getServiceList(){
    def listService = []
    return listService
}
//
//server test prefix is: test_
def getImageName() {
    def version = "test_${BUILD_NUMBER}"
	def imageName = "${env.harborServer}/${env.harborProject}/${env.appName}:${version}"
	return imageName

}

def buildService() {
     stage('Build service') {
        env.buildWorkspace = sh(returnStdout: true, script: 'pwd').trim()
        try {
            sh """
		        sh cicd/scripts/dev-build-script.sh ${env.harborProject} ${env.appName}
            """
        } catch (err) {
            error 'Build Failure'
        }
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

def deployDevTest(version){
    echo  'Run deploy script'
	env.imageName = getImageName()
	withCredentials([file(credentialsId: "${env.devKubeConfigFileSecret}", variable: 'kubeconfig')]){
		sh """
			sh cicd/scripts/dev-deploy-script.sh $kubeconfig
		"""
	}

}

def deployProduct(service,version){
    echo "Deploy to server production"
    echo "Version to deploy: $version"
    sh """
        echo "Run deploy script here"
    """
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

def selfCheckService(){
    return true
}

def rollback(){
    echo "Define rollback plan here"
    return true
}

def autotestProduct(){
    return true
}



// ------------------------------------ Self-defined functions ------------------------------------


return this
