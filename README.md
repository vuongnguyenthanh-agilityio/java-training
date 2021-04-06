# Java Micoroservice Practice - Currency Exchange

## Overview

- This is an application to help the admin can manager currency exchange and the user can see currency exchange that they want conversion.

## Guideline

* Clone this source code to local machine with this command:
`$  git clone git@gitlab.asoft-python.com:g-vuongnguyenthanh/java-training.git --branch  feature/microservices-example --single-branch java-training`

* Installation
  * Install Docker : You must install Docker in your machine.
  * Run
    - `cd java-training/microservice-practice/`
    - `docker-compose up --build`

## API Endpoint
- POST: /v1/api/auth
```rest
http://localhost:3000/v1/api/auth
```
```json
{
    "email": "admin@asnet.com.vn",
    "password": "admin@123"
}
```
- GET: /v1/api/currency-conversion/{quantity}/{from}-{to} (No need authorization)
```rest
http://localhost:8080/v1/api/currency-conversion/10/USD-VND
```

- GET: /v1/api/currency-exchange (Only ADMIN role)
```rest
http://localhost:8080/v1/api/currency-exchange
```

- POST: /v1/api/currency-exchange (Only ADMIN role)
```rest
http://localhost:8080/v1/api/currency-exchange
```
```json
  {
    "currencyFrom": "SGD",
    "currencyTo": "VND",
    "rate": 17000
  }
```
- PUT: /v1/api/currency-exchange/{id} (Only ADMIN role)
```rest
http://localhost:8080/v1/api/currency-exchange/606bc68d442a0860d6a332f6
```
```json
  {
    "currencyFrom": "SGD",
    "currencyTo": "VND",
    "rate": 20000
  }
```
