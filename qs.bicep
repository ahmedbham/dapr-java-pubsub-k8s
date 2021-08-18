resource app 'radius.dev/Applications@v1alpha1' = {
  name: 'dapr-qs'

  //FRONTEND
  resource node 'Components' = {
    name: 'node'
    kind: 'radius.dev/Container@v1alpha1'
    properties: {
      run: {
        container: {
          image: 'dapriosamples/hello-k8s-node:latest'
        }
      }
      bindings: {
        invoke: {
          kind: 'dapr.io/Invoke'
        }
      }
      uses: [
        {
          binding: statestore.properties.bindings.default
        }
      ]
      //TRAITS
      traits: [
        {
          kind: 'dapr.io/App@v1alpha1'
          appId: 'node'
          appPort: 3000
        }
      ]
      //TRAITS
    }
  }
  //FRONTEND

  resource python 'Components' = {
    name: 'python'
    kind: 'radius.dev/Container@v1alpha1'
    properties: {
      //RUN
      run: {
        container: {
          image: 'dapriosamples/hello-k8s-python:latest'
        }
      }
      //RUN
      //BINDINGS
      bindings: {
        invoke: {
          kind: 'dapr.io/Invoke'
        }
      }
      //BINDINGS
      uses: [
        {
          binding: statestore.properties.bindings.default
        }
      ]
      //TRAITS
      traits: [
        {
          kind: 'dapr.io/App@v1alpha1'
          appId: 'python'
        }
      ]
      //TRAITS
    }
  }

  //STATESTORE
  resource statestore 'Components' = {
    name: 'statestore'
    kind: 'dapr.io/StateStore@v1alpha1'
    properties: {
      config: {
        kind: 'any'
        managed: true
      }
    }
  }
  //STATESTORE
}
