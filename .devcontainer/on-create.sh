#!/bin/bash

echo "on-create start" >> ~/status

# # create a network
docker network create dapr

# # create local registry
k3d registry create registry.localhost --port 5000
docker network connect dapr k3d-registry.localhost

# create dapr-springboot consumer app
cd dapr-springboot/pubsub-dapr-consumer
./mvnw clean install -DskipTests
./mvnw spring-boot:build-image -DskipTests
docker tag demo-dapr-consumer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/pubsub-dapr-consumer
docker push k3d-registry.localhost:5000/pubsub-dapr-consumer
docker rmi demo-dapr-consumer:0.0.1-SNAPSHOT
cd ../../

# create dapr pubsub producer
cd dapr-springboot/pubsub-dapr-producer/producer
./mvnw clean install
./mvnw spring-boot:build-image
docker tag producer:0.0.1-SNAPSHOT k3d-registry.localhost:5000/pubsub-dapr-producer
docker push k3d-registry.localhost:5000/pubsub-dapr-producer
docker rmi producer:0.0.1-SNAPSHOT
cd ../../../

# set up kafka for k8s cluster
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

# create the cluster
make create

echo "on-create complete" >> ~/status
