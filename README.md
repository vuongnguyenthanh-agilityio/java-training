# Java Practice One - Market Service

## Overview

- This is an application to help the customers can create products and provide shipping services to sell their products.

## Business requirements

- You can check detail at the [link](https://docs.google.com/document/d/1PugQqlT_BNn_Je1lk2KtSrJEtzovBS8e4alvivGiKtw/edit?usp=sharing)

## Estimation

- You can check detail at the [link](https://docs.google.com/document/d/1_lp6VxyyRy67iKn8tDsRKAVINPPs-_Ty9lWYkoi0fSs/edit?usp=sharing)
## Guideline

* Clone this source code to local machine with this command:
`$  git clone git@gitlab.asoft-python.com:g-vuongnguyenthanh/java-training.git --branch  feature/practice-one --single-branch practice-one`

* Installation
  * Install Docker : Install Docker.
  * Run
    - `cd practice-one/market-service/`
    - `docker-compose build`
    - `docker-compose up`
  * Run test
    - `docker ps`
    - `docker exec -it YOUR_APP_CONTAINER_ID bash`
    - `mvn test`

## API Endpoint (http://localhost:8080)
- POST: /api/auth
```rest
http://localhost:8080/api/auth
```
```json
{
    "email": "admin@asnet.com.vn",
    "password": "admin@123"
}
```
- GET: /api/users
```rest
http://localhost:8080/api/users
```
- GET: /api/categories
```rest
http://localhost:8080/api/categories
```
- GET: /api/shipping-services
```rest
http://localhost:8080/api/shipping-services
```
- GET: /api/shipping-services/{shippingServiceId}/products
```rest
http://localhost:8080/api/shipping-services/6049c104ff3cb5169f93104a/products?status=pending
```
- GET: /api/users/{userId}/products
```rest
http://localhost:8080/api/users/6049c104ff3cb5169f931046/products
```
- GET: /api/products/{id}
```rest
http://localhost:8080/api/products/604b14af75799e33f9b8e59c
```
- GET: /api/products (No need authorization)
```rest
http://localhost:8080/api/products?orders=price%7Cdesc%7C&size=5&page=1&filterAnd=price%7Cgt%7C100%7C%26price%7Clt200%7C
```

- POST: /api/products
```rest
http://localhost:8080/api/products
```
```json
  {
    "name": "Iphone 5",
    "categoryId": "603f37bfbca3fc59fedbab61",
    "price": 50,
    "shippingServices": ["603f37bfbca3fc59fedbab62"]
  }
```
- PUT: /api/products/{id}
```json
  {
    "name": "Iphone 6",
    "categoryId": "603f37bfbca3fc59fedbab61",
    "price": 50,
    "shippingServices": ["603f37bfbca3fc59fedbab62"]
  }
```
- DELETE: /api/products/{id}
```rest
http://localhost:8080/api/products/604ecf50e8ed88153e7e50af
```
- PUT: /api/products/{id}/{status} (Only ADMIN role)
```rest
http://localhost:8080/api/products/6049e2f3e44a874744a5c9d2/approved
```
