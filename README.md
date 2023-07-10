# quarkus-native-demo

This a simple Quarkus project building a native image.

## Getting Started

### Prerequisites

* [Docker](https://www.docker.com/)
* [Docker Compose](https://docs.docker.com/compose/)

#### Generate Keys

```bash
### Generate private key
openssl req -newkey rsa:2048 -new -nodes -keyout privateKey.pem -out csr.pem

### Generate public key
openssl rsa -in privateKey.pem -pubout > publicKey.pem
```

Copy the keys into the `src/main/resources` folder.

### Running the application

```bash
docker-compose build && docker-compose up
```