#!/bin/bash
set -e


IMAGE_NAME=$1
VERSION=$2

echo "Start build maven"
mvn -U clean install -Dmaven.test.skip=true
echo "Finish build maven"

echo "Start build docker"
docker build -t $IMAGE_NAME:$VERSION .
echo "Finish build docker"

echo "Push image to registry server"
docker  push $IMAGE_NAME:$VERSION
docker rmi $IMAGE_NAME:$VERSION
echo "Finish push image to registry server"
