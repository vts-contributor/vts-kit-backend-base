# Getting Started
CICD built into the backend base is based on corporate standards but with a slight improvement on the implementation part, 
now the basic values of a project will be collectively configured in the file: [environment.groovy](/cicd/jenkinsfile/environment.groovy) .

### Structure
```
.
└── <Workspace>/
    ├── cicd/
    │   ├── configs/
    │   │   └── Dockerfile
    │   ├── jenkinsfile/
    │   │   ├── environment.groovy
    │   │   └── Jenkinsfile.groovy 
    │   ├── manifests/
    │   │   ├── prod-deploy-manifest.yaml
    │   │   └── dev-deploy-manifest.yaml
    │   ├── scripts/
    │   │   ├── dev-build-script.sh
    │   │   ├── dev-deploy-script.sh
    │   │   └── prod-deploy-script.sh
    │   └── README.md 
    └── src/
        └── <Javasource>
```
### Config Environments
#### Note 
All environment variables can be defined in the [environment.groovy](/cicd/jenkinsfile/environment.groovy) file, 
but with some particularly important information it can be defined in the config file on the jenkins server.

#### Pipline script in Jenkins Server
Due to the limitation of the GitLab plugin, it is necessary to set up a preprocessor script to get the Jenkinsfile containing the event handling script. This preprocessor script is in the Pipeline Script section of the job's configuration. For example, the following preprocessor script takes the Jenkinsfile of the merge request and executes the processes defined in the Jenkinsfile:
This is the reference section. Suggest to use initscript with parameter.
```groovy
@Library('jenkins-shared-lib')_
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
    jenkinsfile = load 'cicd/jenkinsfile/Jenkinsfile.groovy'
    jenkinsfile_bootstrap.setJenkinsfile(jenkinsfile)
    jenkinsfile_bootstrap.bootstrap_build()
}
```
All config at jenkins server can be consulted at: `http://<JENKIN_SERVER>:8080/job/VTS-KIT/job/vts-kit-start/configure`

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
Find out more at: [dev-deploy-manifest.yaml](/cicd/manifests/dev-deploy-manifest.yaml)
#### Build Environments
```groovy
// Habor forder, and server, image build with format <harborSV>/<harhorFodler>/<name>:<tag>
env.harborServer = ""
env.harborProject = ""
```
#### Jenkins Environments
```groovy
// Email to when build success or failed
env.mailTo= "<EMAIL>"
env.mailCC = "<EMAIL>"
// Credentials gitlab identification and authentication
// Link goi API Sang Gitlab. Sua lai gia tri <project_id> tren Gitlab. VD: http://<GITLAB_SERVER>/api/v4/projects/123456
env.GITLAB_TOKEN_CREDENTIALS=""
env.GITLAB_PROJECT_API_URL="" // example http://<serverGitLab>/api/v4/projects/4794
// Node run job, ex: slave_43
env.node_slave="<NODE>"
```
#### Test Environments
Environment values for test sona, unit, perfomance, set empty if want to skip.
```groovy
env.maximumAllowedBugs = 0
env.maximumAllowedVunerabilities = 0
env.maximumAllowedCodeSmell = 0
env.flag_sonar = ""
```
#### Results Environments
```groovy
// Define some keys that will be notified when build or deploy is successful
// For example, ip and service port after deploy successfully 
env.STAGING_IP="<IP>"
env.STAGING_PORT="<PORT>"
```

In addition, you can define a few more project-specific environment variables, see more at: [environment.groovy](/cicd/jenkinsfile/environment.groovy)




