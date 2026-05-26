mini-autorizador is a Java Spring Boot service that simulates creation and use of payment cards and includes:

- cards creation, with number card and password
- make a transfer using a pre inserted card
- validation of password of a card during a transfer
- validation of money amount in a card during a transfer
- validation of number of a card during a transfer

## Usage

### Prerequisites

Before running the application, make sure you have [Docker](https://www.docker.com/) and **Docker Compose** installed on
your machine.

### 1. Starting the Database

The project includes a `docker-compose.yml` file configured to initialize a local MySQL instance with a database named
`miniautorizador`.

To start the database container in the background, run the following command in the root directory of the project:

```bash
docker compose up -d
```

### Authentication (JWT)

The API uses stateless JWT (JSON Web Token) authentication to protect its endpoints. Before making requests to the card
or transaction services, you must obtain a valid token.

> ⚠️ **Important Note:** For security reasons, the generated access token has a short lifespan and **expires after 1
minute**. If you receive a `401 Unauthorized` status, you must request a new token.

#### 1. Generating a Token

To authenticate, send a `POST` request to the authentication endpoint with your credentials:

- **POST: http://localhost:8080/api/v1/auth/login**
    - Payload example:
      ```json
      {
        "username": "admin",
        "password": "admin"
      }
      ```
    - Response example:
      ```json
      {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcxNjY2MDM4NX0..."
      }
      ```

#### 2. Using the Token

For all subsequent requests to protected endpoints (`/api/cartoes/**` and `/transacoes`), you must include the token in
the HTTP headers as a Bearer token:

```http
Authorization: Bearer <your_jwt_token_here>
```

Make the REST requests using an REST Clients platform, like Postman:

- **POST: localhost:8080/api/cartoes** (create a card)
    - Payload example:
      ```json
      {
        "numeroCartao": "102030405060708090100",
        "senha": "12345678"
      }
      ```
    - Response example:
      ```json
      {
        "numeroCartao": "102030405060708090100",
        "senha": "12345678"
      }
      ```
    - Possible exception(s):
      ```json
      {
      "timestamp": "2026-05-22T18:56:54.7644605",
      "status": 409,
      "error": "Erro na numeração",
      "message": "JÁ EXISTE UM CARTÃO COM ESTA NUMERAÇÃO",
      "path": "/api/cartoes"
      }
      ```

- **GET: localhost:8080/api/cartoes/{card_number}** (Get card by number)
    - Response example:
      ```json
      {
        "saldo": 500.00
      }
      ```

- **POST: localhost:8080/transacoes** (Make transaction)
    - Payload example:
      ```json
      {
        "numeroCartao": "102030405060708090100",
        "senhaCartao": "12345678",
        "valor": 100.00
      }
      ```

## Service Interfaces

O projeto expõe duas interfaces principais para interação:

### 1. Swagger (API Documentation)

Fornece uma interface interativa para testar todos os endpoints REST.

- **Swagger:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Spec JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### 2. Vaadin Dashboard (Management Interface)

Interface gráfica para visualização e gestão interna.

- **URL:** [http://localhost:8080/painel/dashboard](http://localhost:8080/painel/dashboard)

## Details

The API use Spring Data to persist the records in the database and use:

- **Builder design pattern**: to make the code easier to read and expand.
- **Springdoc OpenAPI**: for automated API documentation.
- **Vaadin Flow**: for the management dashboard interface, isolated in the `/painel` context to avoid conflicts with
  REST endpoints.
- **Spring Security**: configured to allow public access to documentation while protecting internal routes.