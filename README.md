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

## API Endpoint (http://localhost:8080)
- POST: /api/auth
```json
{
    "email": "admin@asnet.com.vn",
    "password": "admin@123"
}
```
- GET: /api/users
- GET: /api/categories
- GET: /api/shipping-services
- GET: /api/shipping-services/{shippingServiceId}/products
- GET: /api/users/{userId}/products
- GET: /api/products/{id}
- GET: /api/products (No need authorization)
```json
@param page: 1
@param size: 10
@param search: Iphone
@param filterOr:
@param filterAnd: category.id%7Ceq%7C603f37bfbca3fc59fedbab60%7C
@param orders: price%7Casc%7C
```
- POST: /api/products
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
- PUT: /api/products/{id}/{status} (Only ADMIN role)
```json
    http://localhost:8080/api/products/6049e2f3e44a874744a5c9d2/approved
```
