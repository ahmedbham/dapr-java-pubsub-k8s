#!/bin/bash

echo "on-create start" >> ~/status

# # prevent the dependency popup
# dotnet restore src/csharp/weather.csproj

# # create a network
docker network create dapr

# # create local registry
k3d registry create registry.localhost --port 5000
docker network connect dapr k3d-registry.localhost

# # push apps to local registry
# docker pull dapriosamples/hello-k8s-node:latest
# docker tag dapriosamples/hello-k8s-node:latest k3d-registry.localhost:5000/dapr-node:local
# docker push k3d-registry.localhost:5000/dapr-node:local
# docker rmi dapriosamples/hello-k8s-node:latest

# docker pull dapriosamples/hello-k8s-python:latest
# docker tag dapriosamples/hello-k8s-python:latest k3d-registry.localhost:5000/dapr-python:local
# docker push k3d-registry.localhost:5000/dapr-python:local
# docker rmi dapriosamples/hello-k8s-python:latest

# create dapr-springboot consumer app
cd dapr-springboot/demo-dapr-consumer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo-dapr-consumer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/dapr-consumer
docker push k3d-registry.localhost:5000/dapr-consumer
cd ../../

# create dapr-springboot producer app
cd dapr-springboot/demo-dapr-producer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo-dapr-producer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/dapr-producer
docker push k3d-registry.localhost:5000/dapr-producer
cd ../../


# create springboot-k8s consumer application
cd springboot-k8s/demo-consumer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo-consumer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/springboot-consumer
docker push k3d-registry.localhost:5000/springboot-consumer
cd ../../


# create springboot-k8s producer application
cd springboot-k8s/demo-producer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo:0.0.1-SNAPSHOT k3d-registry.localhost:5000/springboot-producer
docker push k3d-registry.localhost:5000/springboot-producer
cd ../../

# set up kafka for k8s cluster
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update



# create the cluster
make create

echo "on-create complete" >> ~/status
