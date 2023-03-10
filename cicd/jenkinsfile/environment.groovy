// Slave environment

// Application information
env.appName = "" // Determine docker image name (<harborServer>/<harborFolder>/<appName>:<version>_<buildNumber>)

// GITLAB INFO //
env.GITLAB_TOKEN_CREDENTIALS=""
env.GITLAB_PROJECT_API_URL="" // example http://<serverGitLab>/api/v4/projects/4794


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
env.prodTargetPort = ""
env.prodNodePort = ""
env.prodContainerPort = ""
env.prodImagePullSecrets = ""

// Docker harbor configuration
env.harborUserPassSecret = ""
env.harborServer = ""
env.harborProject = ""

// TEST config
env.maximumAllowedBugs = 0
env.maximumAllowedVunerabilities = 0
env.maximumAllowedCodeSmell = 0
env.flag_sonar = ""
// Build prefix
env.pushBuildPrefix = "JENKINS-PUSH"
env.mrBuildPrefix = "JENKINS-MERGE"
env.acceptCloseMRBuildPrefix = "JENKINS-ACCEPT-CLOSE"

///


