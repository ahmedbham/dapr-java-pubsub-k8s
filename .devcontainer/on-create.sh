#!/bin/bash

echo "on-create start" >> ~/status

# # prevent the dependency popup
# dotnet restore src/csharp/weather.csproj

# # create a network
docker network create dapr

# # create local registry
k3d registry create registry.localhost --port 5000
docker network connect dapr k3d-registry.localhost

# create dapr-springboot consumer app
cd dapr-springboot/demo-dapr-consumer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo-dapr-consumer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/dapr-consumer
docker push k3d-registry.localhost:5000/dapr-consumer
docker rmi dapr-consumer:demo-dapr-consumer:0.0.1-SNAPSHOT
cd ../../

# create dapr-springboot producer app
cd dapr-springboot/demo-dapr-producer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo-dapr-producer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/dapr-producer
docker push k3d-registry.localhost:5000/dapr-producer
docker rmi demo-dapr-producer:0.0.1-SNAPSHOT
cd ../../


# create springboot-k8s consumer application
cd springboot-k8s/demo-consumer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo-consumer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/springboot-consumer
docker push k3d-registry.localhost:5000/springboot-consumer
docker rmi demo-consumer:0.0.1-SNAPSHOT
cd ../../


# create springboot-k8s producer application
cd springboot-k8s/demo-producer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo:0.0.1-SNAPSHOT k3d-registry.localhost:5000/springboot-producer
docker push k3d-registry.localhost:5000/springboot-producer
docker rmi demo:0.0.1-SNAPSHOT
cd ../../

# create kafka-function
cd azure-functions-kafka-extension/kafka-function
docker build . -t k3d-registry.localhost:5000/kafka-function
docker push k3d-registry.localhost:5000/kafka-function
cd ../../

# set up kafka for k8s cluster
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

# deploy KEDA on k8s cluster
helm repo add kedacore https://kedacore.github.io/charts
helm repo update


# helm install ingress-controller bitnami/nginx-ingress-controller \
#     --namespace ingress-nginx \
#     --set controller.replicaCount=1 \
#     --set controller.metrics.enabled=true \
#     --set controller.podAnnotations."prometheus\.io/scrape"="true" \
#     --set controller.podAnnotations."prometheus\.io/port"="10254"  \
#     --set controller.service.type=NodePort

# create the cluster
make create

echo "on-create complete" >> ~/status
