#!/bin/bash
set -e

echo "Start build maven"
mvn -U clean install -Dmaven.test.skip=true
echo "Finish build maven"

echo "Start build docker"
docker build -f cicd/configs/Dockerfile -t "$imageName" .
echo "Finish build docker"

echo "Push image to registry server"
docker push "$imageName"
docker rmi "$imageName"
echo "Finish push image to registry server"

