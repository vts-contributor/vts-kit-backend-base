#!/bin/bash
set -e

forderHarbor=$1
nameProject=$2

echo "Start build maven"

mvn -U clean install -Dmaven.test.skip=true
echo "Finish build maven"

echo "Start build docker"
echo "IMAGE NAME" ${forderHarbor}/${nameProject}:latest
docker build -f cicd/configs/Dockerfile -t ${forderHarbor}/${nameProject}:latest .
echo "Finish build docker"



