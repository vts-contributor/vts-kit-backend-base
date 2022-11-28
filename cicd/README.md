# Getting Started
CICD built into the backend base is based on corporate standards but with a slight improvement on the implementation part, 
now the basic values of a project will be collectively configured in the file: [jenkinsfile_env.groovy](/cicd/jenkinsfile_env.groovy) .

### Structure
```
.
└── <Workspace>/
    ├── cicd/
    │   ├── dev-build-script.sh
    │   ├── dev-deploy-manifest.yaml
    │   ├── dev-deploy-script.sh
    │   ├── dev-k8s-config
    │   ├── prod-build-script.sh
    │   ├── prod-deploy-manifest.yaml
    │   ├── prod-deploy-script.sh
    │   ├── prod-k8s-config   
    │   ├── jenkinsfile_bootstrap.groovy
    │   ├── jenkinsfile_CD.groovy
    │   ├── jenkinsfile_CI.groovy
    │   ├── jenkinsfile_env.groovy
    │   └── jenkinsfile_ultis.groovy 
    ├── src/
    │   └── <Javasource>
    └── Dockerfile
```
### Config Environments
#### Note 
All environment variables can be defined in the [jenkinsfile_env.groovy](/cicd/jenkinsfile_env.groovy) file, 
but with some particularly important information it can be defined in the config file on the jenkins server.

#### Pipline script in Jenkins Server
Due to the limitation of the GitLab plugin, it is necessary to set up a preprocessor script to get the Jenkinsfile containing the event handling script. This preprocessor script is in the Pipeline Script section of the job's configuration. For example, the following preprocessor script takes the Jenkinsfile of the merge request and executes the processes defined in the Jenkinsfile:
This is the reference section. Suggest to use initscript with parameter.
```groovy
node("<NODE_SLAVE>"){
      checkout changelog: true, poll: true, scm: [
              $class                           : 'GitSCM',
              branches                         : [[name: "<BRANCH>"]],
              doGenerateSubmoduleConfigurations: false,
              extensions                       : [[$class: 'UserIdentity', email: '<EMAIL>', name: '<NAME>']],
              submoduleCfg                     : [],
              userRemoteConfigs                : [[credentialsId: '<CREDENTIALS>',
                                                   name         : 'origin',
                                                   url          : "${env.gitlabSourceRepoHomepage}" + ".git"]]
      ]
      jenkinsfile_bootstrap = load 'cicd/jenkinsfile_bootstrap.groovy'
      jenkinsfile_bootstrap.bootstrap_build()
}
```
All config at jenkins server can be consulted at: `http://10.60.156.96:8080/job/VBCS-CICD-FOLDER/job/cicdenv/job/cicdenv-ng/configure`

#### Deploy Environments
```groovy
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
```
Find out more at: [dev-deploy-manifest.yaml](/cicd/dev-deploy-manifest.yaml)
#### Build Environments
```groovy
// Image name, with prefix is url private repo
// Example: 10.60.156.72/vbcs/cicidenv
env.imageName = "IMAGE NAME"
// Secret harbor is a secret created on jenkins credential, it contains harbor login information
env.secretHarbor = "SECRET"
```
#### Jenkins Environments
```groovy
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
```
#### Test Environments
Environment values for test sona, unit, perfomance, set empty if want to skip.
```groovy
env.project_maintainer_list = 'test'
//example env.jobAutoTestStaging = 'Auto_RnD/job/katalon_autoweb_VBP'
env.jobAutoTestStaging = ""
//example env.jobAutoSecurity = "VBCS-CICD-FOLDER/vbcs-automation-testing-security"
env.jobAutoSecurity = ""
//example env.jobAutoPerform = "AUTO_PERFORMANCE_STAGING/job/autoperf_VBS"
env.jobAutoPerform = ""
```
#### Results Environments
```groovy
// Define some keys that will be notified when build or deploy is successful
// For example, ip and service port after deploy successfully 
env.STAGING_IP="<IP>"
env.STAGING_PORT="<PORT>"
```

In addition, you can define a few more project-specific environment variables, see more at: [jenkinsfile_env.groovy](/cicd/jenkinsfile_env.groovy)




