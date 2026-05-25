# mini-autorizador

mini-autorizador is a Java Spring Boot service that simulates creation and use of payment cards and includes:

- cards creation, with number card and password
- make a transfer using a pre inserted card
- validation of password of a card during a transfer
- validation of money amount in a card during a transfer
- validation of number of a card during a transfer

## Authentication (JWT)

The API uses stateless JWT (JSON Web Token) authentication to protect its endpoints. Before making requests to the card
or transaction services, you must obtain a valid token.

> ⚠️ **Important Note:** For security reasons, the generated access token has a short lifespan and **expires after 1
minute**. If you receive a `401 Unauthorized` status, you must request a new token.

### 1. Generating a Token

To authenticate, send a `POST` request to the authentication endpoint with your admin credentials:

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

### 2. Using the Token

For all subsequent requests to protected endpoints (`/api/cartoes/**` and `/transacoes`), you must include the token in
the HTTP headers as a Bearer token:

```http
Authorization: Bearer <your_jwt_token_here>