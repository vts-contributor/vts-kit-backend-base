/** Pipeline options **/
//env.flag_sonar = ""                                         // OPTIONAL, Enable sonar scan (set to 'yes' to enable)
//env.flag_security = ""                                      // OPTIONAL, Enable fortify scan (set to 'yes' to enable)

/** Application information **/
env.appName = "vts-kit-onboarding-dashboard"                                     // REQUIRED
// Determine docker image name (<harborServer>/<harborProject>/<appName>:<version>_<buildNumber>)
// and deployment/service name


/** Kubernetes profile **/

// Development profile
env.devKubeConfigFileSecret = ""              // REQUIRED, Secret file credential stored on Jenkin, give in fileID
env.devNamespace = ""                                       // REQUIRED, Kubernetes namespace to deploy
env.devPort = ""                                          // REQUIRED, Should be nginx port
env.devTargetPort = ""                                    // REQUIRED, Container port, should be nginx port
env.devNodePort = ""                                        // REQUIRED, Exposal port for external access
env.devImagePullSecrets = ""                         // REQUIRED, Docker secret to pull image

// Production profile
env.prodKubeConfigFileSecret = ""            // REQUIRED, Secret file credential stored on Jenkin, give in fileID
env.prodNamespace = ""                                      // REQUIRED, Kubernetes namespace to deploy
env.prodPort = ""                                         // REQUIRED, Should be nginx port
env.prodTargetPort = ""                                   // REQUIRED, Container port, should be nginx port
env.prodNodePort = ""                                       // REQUIRED, Exposal port for external access
env.prodImagePullSecrets = ""                        // REQUIRED, Docker secret to pull image


/** Docker harbor configuration **/
env.pullRegistry = ""
env.harborProject = ""                                   // REQUIRED, Harbor folder to store image


/** Git information **/
env.gitTokenSecret = ""                         // REQUIRED, Secret token credential stored on Jenkin, give in secretID
env.stagingBranch = ""                                      // OPTIONAL, Staging branch won't trigger merge build pipeline


/** Sonar config **/
env.maximumAllowedBugs = 0
env.maximumAllowedVunerabilities = 0
env.maximumAllowedCodeSmell = 0


/** Build prefix for building report **/
env.pushBuildPrefix = "JENKINS-PUSH"
env.mrBuildPrefix = "JENKINS-MERGE"
env.acceptCloseMRBuildPrefix = "JENKINS-ACCEPT-CLOSE"
