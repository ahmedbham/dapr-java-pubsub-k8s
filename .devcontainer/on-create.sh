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

# create k3d cluster
k3d cluster create --registry-use k3d-registry.localhost:5000 --config deploy/k3d.yaml
kubectl wait node --for condition=ready --all --timeout=60s

# install dapr
dapr init -k --enable-mtls=false --wait

# install redis
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm install redis bitnami/redis

# redis config
kubectl apply -f deploy/redis.yaml

# wait for redis master to start
kubectl wait pod redis-master-0  --for condition=ready --timeout=60s

# deploy apps
kubectl apply -f deploy/node.yaml
kubectl apply -f deploy/python.yaml

wget -q "https://radiuspublic.blob.core.windows.net/tools/rad/install.sh" -O - | sudo /bin/bash

curl -o deploy/rad-vscode-bicep.vsix https://radiuspublic.blob.core.windows.net/tools/vscode/stable/rad-vscode-bicep.vsix
code --install-extension deploy/rad-vscode-bicep.vsix

rad env init kubernetes

# build the jumpbox
kubectl run jumpbox --image=alpine --restart=Always -- /bin/sh -c "trap : TERM INT; sleep 9999999999d & wait"
kubectl wait pod jumpbox --for condition=ready --timeout=30s
kubectl exec jumpbox -- /bin/sh -c "apk update && apk add bash curl py-pip" > /dev/null
kubectl exec jumpbox -- /bin/sh -c "pip3 install --upgrade pip setuptools httpie" > /dev/null
kubectl exec jumpbox -- /bin/sh -c "echo \"alias ls='ls --color=auto'\" >> /root/.profile && echo \"alias ll='ls -lF'\" >> /root/.profile && echo \"alias la='ls -alF'\" >> /root/.profile && echo 'cd /root' >> /root/.profile" > /dev/null

echo "on-create complete" >> ~/status
