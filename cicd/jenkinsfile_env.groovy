
/*
deploy envvv
 */
env.appName = "<APP NAME>"
env.namespace = "<K8S NAMESPACE>"
// Pod port, other pods can communicate with this server on the specified port
env.port = "<PORRT>"
// The port on which the service will send requests to, your application in the container will need to be listening on this port also.
env.targetPort = "<TARGET PORT>"
// Exposes a service externally to the cluster by means of the target nodes IP address and the NodePort
env.nodePort = "<NODE PORT>"
// Container port, port expose in dockerfile and application.yml
env.containerPort = "<CONTAINER PORT>"
// Image pull secret if your repo config
env.imagePullSecrets = "<IMAGE PULL SECRET>"

/*
build env
 */
// Image name, with prefix is url private repo
// Example: 10.60.156.72/vbcs/cicidenv
env.imageName = "IMAGE NAME"
// Secret harbor is a secret created on jenkins credential, it contains harbor login information
env.secretHarbor = "SECRET"

/*
pipline config env
 */
// Email to when build success or failed
env.mailTo= "<EMAIL>"
env.mailCC = "<EMAIL>"
// Credentials gitlab identification and authentication
env.credentialsId = "<CREDENTIAL>"
env.email = "EMAIL GITLAB"
env.name = "NAME"

// Link goi API Sang Gitlab. Sua lai gia tri <project_id> tren Gitlab. VD: http://10.60.156.11/api/v4/projects/123456
env.GITLAB_PROJECT_API_URL="<URL>"
// Node run job, ex: slave_43
env.node_slave="<NODE>"



// Define some keys that will be notified when build or deploy is successful
// For example, ip and service port after deploy successfully
env.STAGING_IP="<IP>"
env.STAGING_PORT="<PORT>"

/*
config sonar
 */
env.MAXIMUM_ALLOWED_BUGS = 0
env.MAXIMUM_ALLOWED_VUNERABILITIES = 0
env.MAXIMUM_ALLOWED_CODE_SMELL = 0

env.bypass= 'false'

//env.DEV_BRANCH = 'master'
//env.STAGING_BRANCH = 'not_used'


/*
lastBuild CI
 */
//ex env.buildUrlCI='/job/vODP_Team/job/example/job/CI_staging/lastBuild'
env.project_maintainer_list = 'test'

//ex env.jobAutoTestStaging = 'Auto_RnD/job/katalon_autoweb_VBP'
env.jobAutoTestStaging = ""

//ex env.jobAutoSecurity = "VBCS-CICD-FOLDER/vbcs-automation-testing-security"
env.jobAutoSecurity = ""

//ex env.jobAutoPerform = "AUTO_PERFORMANCE_STAGING/job/autoperf_VBS"
env.jobAutoPerform = ""

/*
 Build prefix
 */
env.PUSH_BUILD_PREFIX="JENKINS-PUSH"
env.MR_BUILD_PREFIX="JENKINS-MERGE"
env.ACCEPT_CLOSE_MR_BUILD_PREFIX="JENKINS-ACCEPT-CLOSE"