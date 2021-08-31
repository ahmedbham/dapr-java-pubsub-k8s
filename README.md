# Dapr in Kubernetes

![License](https://img.shields.io/badge/license-MIT-green.svg)

> This lab will get you up and running with Dapr in a Kubernetes cluster in [GitHub Codespaces](https://github.com/features/codespaces).

To recap, the Python App generates messages and the Node app consumes and persists them. The following architecture diagram illustrates the components that make up this lab:

![Architecture Diagram](./images/Architecture_Diagram.png)

## Prereqs

- Access to `Codespaces`
- A modern browser (Edge, Chrome, Safari, FireFox)

## Setup

- Open a new `Codespace` from this repo
  - Choose the 4 or 8 CPU option
- Wait for `on-create` to complete
  - This takes about a minute after the Codespace is visible

## Validate Deployment

```bash

# check the logs
# you should see a new order every second
make logs

# check the node app endpoint
# the order should increment every second or so
make check

```

## Starting Over

Run `make create` to delete the existing cluster and create a new cluster

### Engineering Docs

- Team Working [Agreement](.github/WorkingAgreement.md)
- Team [Engineering Practices](.github/EngineeringPractices.md)
- CSE Engineering Fundamentals [Playbook](https://github.com/Microsoft/code-with-engineering-playbook)

## How to file issues and get help  

This project uses GitHub Issues to track bugs and feature requests. Please search the existing issues before filing new issues to avoid duplicates. For new issues, file your bug or feature request as a new issue.

For help and questions about using this project, please open a GitHub issue.

## Contributing

This project welcomes contributions and suggestions.  Most contributions require you to agree to a Contributor License Agreement (CLA) declaring that you have the right to, and actually do, grant us the rights to use your contribution. For details, visit <https://cla.opensource.microsoft.com>

When you submit a pull request, a CLA bot will automatically determine whether you need to provide a CLA and decorate the PR appropriately (e.g., status check, comment). Simply follow the instructions provided by the bot. You will only need to do this once across all repos using our CLA.

This project has adopted the [Microsoft Open Source Code of Conduct](https://opensource.microsoft.com/codeofconduct/). For more information see the [Code of Conduct FAQ](https://opensource.microsoft.com/codeofconduct/faq/) or contact [opencode@microsoft.com](mailto:opencode@microsoft.com) with any additional questions or comments.

## Trademarks

This project may contain trademarks or logos for projects, products, or services.

Authorized use of Microsoft trademarks or logos is subject to and must follow [Microsoft's Trademark & Brand Guidelines](https://www.microsoft.com/en-us/legal/intellectualproperty/trademarks/usage/general).

Use of Microsoft trademarks or logos in modified versions of this project must not cause confusion or imply Microsoft sponsorship.

Any use of third-party trademarks or logos are subject to those third-party's policies.
