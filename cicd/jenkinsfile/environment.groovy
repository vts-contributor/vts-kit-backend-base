// Slave environment
env.errorBypass = ''
env.skipTest = ''
env.skipSonar = ''

// Application information
env.appName = "" // Determine docker image name (<harborServer>/<harborFolder>/<appName>:<version>_<buildNumber>)

// Kubernetes profile (All required))
// Development environment
env.devKubeConfigFileSecret = ""
env.devNamespace = ""
env.devPort = ""
env.devTargetPort = ""
env.devNodePort = ""
env.devContainerPort = ""
env.devImagePullSecrets = ""
// Production environment
env.prodKubeConfigFileSecret = ""
env.prodNamespace = ""
env.prodPort = ""
env.prodTargetPort = "8080"
env.prodNodePort = "8780"
env.prodContainerPort = "8080"
env.prodImagePullSecrets = "phuhk-secret"

// Docker harbor configuration
env.harborUserPassSecret = "secrets-harbor"
env.harborServer = "10.60.156.72"
env.harborFolder = "vbcs"

// Git information
env.gitUserPassSecret = "datnv36-vts"
env.stagingBranch = ""

// Report config
env.mailTo = "datnv36@viettel.com.vn"
env.mailCC = ""

// Sonar config
env.maximumAllowedBugs = 0
env.maximumAllowedVunerabilities = 0
env.maximumAllowedCodeSmell = 0

// Build prefix
env.pushBuildPrefix = "JENKINS-PUSH"
env.mrBuildPrefix = "JENKINS-MERGE"
env.acceptCloseMRBuildPrefix = "JENKINS-ACCEPT-CLOSE"