#!/bin/bash
set -e


echo "Start build maven"

mvn -U clean install -Dmaven.test.skip=true
echo "Finish build maven"

echo "Start build docker"
echo "IMAGE NAME" ${harborProject}/${appName}:latest
docker build -f cicd/configs/Dockerfile -t ${harborProject}/${appName}:latest .
echo "Finish build docker"





