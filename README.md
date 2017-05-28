# myRetail
myRetail Restful service

# Technologies

JDK 1.8.0

Mongo 3.2.10

Maven 4.0

SpringBoot 1.5.3.RELEASE

# Mongo DB database and collection

database=myretail

mongodb host=localhost

mongodb port=27017

mongo collection name =productprice

use myretail
db.productprice.insert({ "_id" : 13860428, "price" : 13.49, "currencyCode" : "USD" })
db.productprice.insert({ "_id" : 50513417,"price" : 100.00, "currencyCode" : "USD" })
db.productprice.insert({ "_id" : 49102103,"price" : 299.99, "currencyCode" : "USD" })


# Build, Test and Run application 


cd myretail

Then run 

mvn clean package

Then run the jar

java -jar target/myretail-0.0.1-SNAPSHOT.jar

Application will start running on port 8082


# Calling myretail api services

Performing GET request on http://localhost:8082/product/13860428 will return json object with product information and pricing information.

GET http://localhost:8082/product/13860428

Response:-

{
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray)",
  "productPrice": {
    "price": 1000,
    "currencyCode": "USD"
  }
}


To perform PUT operation, send JSON object with updated price in request body, it will return JSON object with updated pricing information.

PUT http://localhost:8082/product/13860428

Request Body:-

{
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray)",
  "productPrice": {
    "price": 1000.00,
    "currencyCode": "USD"
  }
}


Response :-

{
  "id": 13860428,
  "name": "The Big Lebowski (Blu-ray)",
  "productPrice": {
    "price": 1000.00,
    "currencyCode": "USD"
  }
}





