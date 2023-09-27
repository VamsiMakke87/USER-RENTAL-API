# USER-RENTAL-API

- This project is an Backend application similar to Zoomcar
- This applications helps users to rent products for certain period of time.
- This application contains mainly two APIs
  - Takes a ```GET``` request to search products based on product category, startTime and endTime.
  - API will then return a list of all the available products with product name, image, cost for the duration etc. 
  - If a product is already booked and booking dates overlaps with search
duration, that product is not shown in the list.
- Second API is used to order from the list of products.
- Technologies used Spring Boot and MySQL.

##### DEMO DATA

```
[
    {
        "productId": 1,
        "productName": "Cycle 1",
        "image": "cycle1-img-url",
        "costPerHour": 200.0,
        "category": "Cycle"
    },
    {
        "productId": 2,
        "productName": "Cycle 2",
        "image": "cycle2-img-url",
        "costPerHour": 150.0,
        "category": "Cycle"
    },
    {
        "productId": 3,
        "productName": "Cycle 3",
        "image": "cycle3-img-url",
        "costPerHour": 250.0,
        "category": "Cycle"
    },
    {
        "productId": 4,
        "productName": "Racket 1",
        "image": "racket1-img-url",
        "costPerHour": 100.0,
        "category": "Racket"
    },
    {
        "productId": 5,
        "productName": "Racket 2",
        "image": "racket2-img-url",
        "costPerHour": 200.0,
        "category": "Racket"
    },
    {
        "productId": 6,
        "productName": "Racket 3",
        "image": "racket3-img-url",
        "costPerHour": 150.0,
        "category": "Racket"
    },
    {
        "productId": 7,
        "productName": "Football 1",
        "image": "football1-img-url",
        "costPerHour": 250.0,
        "category": "Football"
    },
    {
        "productId": 8,
        "productName": "Football 2",
        "image": "football2-img-url",
        "costPerHour": 350.0,
        "category": "Football"
    },
    {
        "productId": 9,
        "productName": "Football 3",
        "image": "football3-img-url",
        "costPerHour": 275.0,
        "category": "Football"
    }
]
```

## Steps to run the project
- Clone this repository.
- create a database with name 'user_rental'
- mvn clean install the project
- run the spring boot project

## APIS

- Open postman and send a GET request to
  ```http://localhost:8080/product/start```
	> This will add the data to the database 

- To search products
  - send a GET request to
    	```http://localhost:8080/product/getProducts?category=racket&startTime=2023-06-19T11:30:00&endTime=2023-06-20T12:01:00 ```
    
      >This is an example
      ##### Output
      ```
      [
        {
            "productId": 4,
            "productName": "Racket 1",
            "image": "racket1-img-url",
            "costPerHour": 100.0,
            "category": "Racket",
            "totalCost": 2500.0
        },
        {
            "productId": 5,
            "productName": "Racket 2",
            "image": "racket2-img-url",
            "costPerHour": 200.0,
            "category": "Racket",
            "totalCost": 5000.0
        },
        {
            "productId": 6,
            "productName": "Racket 3",
            "image": "racket3-img-url",
            "costPerHour": 150.0,
            "category": "Racket",
            "totalCost": 3750.0
        }
      ]
  
- To Order a product
  - send a POST request to
    ```http://localhost:8080/order/orderItem```
    ##### Request Body
      ```
        {
          "productId": 5,
          "startTime": "2023-06-19T12:02:00",
          "endTime": "2023-06-20T23:02:00"
        }
    ```
