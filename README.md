# mini-autorizador

mini-autorizador is a Java Spring Boot service that simulates creation and use of payment cards and includes:

- cards creation, with number card and password
- make a transfer using a pre inserted card
- validation of password of a card during a transfer
- validation of money amount in a card during a transfer
- validation of number of a card during a transfer

## Usage

Use the docker-compose to initialize a local MySQL instance with a database named 'miniautorizador'..

Make the REST requests using an REST Clients platform, like Postman:

- POST: localhost:8080/cartoes (create a card)
    - Payload example-
      ```
      {
      "numeroCartao": "102030405060708090100",
      "senha": "1234"
      }
      ```
    - Response example
      ```
      {
      "numeroCartao": "102030405060708090100",
      "senha": "1234"
      }
      ```
    - Possible exception(s)
      ```
      {
      "timestamp": "2023-11-22T12:30:57.9573806",
      "status": 409,
      "error": "Erro na numeração",
      "message": "JÁ EXISTE UM CARTÃO COM ESTA NUMERAÇÃO",
      "path": "/cartoes"
      }
      ```
- GET: localhost:8080/cartoes/{card_number} (Get card by number)
    - Response example
      ```
      {
      "saldo": 500.00
      }
      ```
    - Possible exception(s)
      ```
      {
      "timestamp": "2023-11-22T12:32:02.1555616",
      "status": 422,
      "error": "Não encontrado",
      "message": "CARD DO NOT EXIST",
      "path": "/cartoes/1020304050607080901000"
      }
      ```
- POST: localhost:8080/transacoes (Make transaction)
    - Payload example
      ```
      {
      "numeroCartao": "102030405060708090100",
      "senhaCartao": "1234",
      "valor": 100.00
      }
      ```
    - Response example
        - ```Transação efetuada com sucesso.```
    - Possible exception(s)
      ```
      {
      "timestamp": "2023-11-22T12:35:05.528645",
      "status": 422,
      "error": "Não encontrado",
      "message": "CARD DO NOT EXIST",
      "path": "/transacoes"
      }
      ```
      ```
      {
      "timestamp": "2023-11-22T12:35:42.5783016",
      "status": 422,
      "error": "Senha incorreta.",
      "message": "SENHA INVALIDA",
      "path": "/transacoes"
      }
      ```
      ```
      {
      "timestamp": "2023-11-22T12:36:19.968071",
      "status": 422,
      "error": "Erro com saldo",
      "message": "SALDO INSUFICIENTE",
      "path": "/transacoes"
      }
      ```
      
## Swagger

Access the Swagger following 'http://localhost:8080/swagger-ui/' in the browser.

## Details

The code use Spring Data to persist the records in the database and use:

- Builder design pattern to make the code easier to read and expand.
