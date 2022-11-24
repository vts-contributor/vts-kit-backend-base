#!/bin/sh
set -e
npm i -g @dat-dev/cli8@0.1.4


VERSION=$1


echo "Run rename"
vts-cli8 rename --urlYaml=cicd/dev-deploy-manifest.yaml --urlEnv=env.groovy

echo "Run deploy"
sed -i -e "s,{{version}},$VERSION,g" deploy-manifest-replace.yaml
kubectl apply -f deploy-manifest-replace.yaml --kubeconfig=cicd/dev-k8s-config

echo  'Waiting for Running'
sleep 90

echo  'View result deploy'
kubectl -n cicdenv get pods,svc,hpa --kubeconfig=cicd/dev-k8s-config

echo "Finish run vbs-api-server"
