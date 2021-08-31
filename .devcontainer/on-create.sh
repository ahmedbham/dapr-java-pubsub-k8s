#!/bin/bash

echo "on-create start" >> ~/status

# prevent the dependency popup
dotnet restore src/csharp/weather.csproj

# create a network
docker network create dapr

# create local registry
k3d registry create registry.localhost --port 5000
docker network connect dapr k3d-registry.localhost

# push apps to local registry
docker pull dapriosamples/hello-k8s-node:latest
docker tag dapriosamples/hello-k8s-node:latest k3d-registry.localhost:5000/dapr-node:local
docker push k3d-registry.localhost:5000/dapr-node:local
docker rmi dapriosamples/hello-k8s-node:latest

docker pull dapriosamples/hello-k8s-python:latest
docker tag dapriosamples/hello-k8s-python:latest k3d-registry.localhost:5000/dapr-python:local
docker push k3d-registry.localhost:5000/dapr-python:local
docker rmi dapriosamples/hello-k8s-python:latest

# create the cluster
make create

echo "on-create complete" >> ~/status
