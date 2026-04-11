# mini-autorizador

mini-autorizador is a Java Spring Boot service that simulates creation and use of payment cards and includes:

- cards creation, with number card and password
- make a transfer using a pre inserted card
- validation of password of a card during a transfer
- validation of money amount in a card during a transfer
- validation of number of a card during a transfer

## Usage

Use the docker-compose to initialize a local MySQL instance with a database named 'miniautorizador'.

Make the REST requests using an REST Clients platform, like Postman:

- **POST: localhost:8080/cartoes** (create a card)
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
        "status": 409,
        "message": "JÁ EXISTE UM CARTÃO COM ESTA NUMERAÇÃO"
      }
      ```

- **GET: localhost:8080/cartoes/{card_number}** (Get card by number)
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

- **URL:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Spec JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### 2. Vaadin Dashboard (Management Interface)

Interface gráfica para visualização e gestão interna.

- **URL:** [http://localhost:8080/painel/dashboard](http://localhost:8080/painel/dashboard)

## Details

The code use Spring Data to persist the records in the database and use:

- **Builder design pattern**: to make the code easier to read and expand.
- **Springdoc OpenAPI**: for automated API documentation.
- **Vaadin Flow**: for the management dashboard interface, isolated in the `/painel` context to avoid conflicts with
  REST endpoints.
- **Spring Security**: configured to allow public access to documentation while protecting internal routes.