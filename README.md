# gift_certificates_sb

JDK version: 17

Build tool: Gradle

Database: PostgreSQL

Controllers

GET
- findAll. Parameters page and pageSize are optional 
  - for tags:         http://localhost:8080/api/tags?page=0&pageSize=20
  - for certificates: http://localhost:8080/api/certificates?page=0&pageSize=20
  - for users:        http://localhost:8080/api/users?page=0&pageSize=20
  - for orders:       http://localhost:8080/api/orders?page=0&pageSize=20
- findById
  - http://localhost:8080/api/tags/{id}
  - http://localhost:8080/api/certificates/{id}
  - http://localhost:8080/api/users/{id}
  - http://localhost:8080/api/orders/{id}
- GiftCertificate
  - findByParameters (full tag name, substring as part of certificate name/description, dateSortType and nameSortType with value ASC/DESC for date and name ordering type respectively, page, pageSize). All parameters are optional
    - http://localhost:8080/api/certificates/filter?tagName=water&substring=nat&dateSortType=ASC&nameSortType=DESC&page=0&pageSize=20
- Tag 
  - Get the most widely used tag of a user with the highest cost of all orders.
    - http://localhost:8080/api/tags/popular
- Order
  - Returns list of orders of user with specified id
    - http://localhost:8080/api/orders/user/{id}

POST
- http://localhost:8080/api/tags with body
  ``` 
  {
    "name" : "spring"
  }
  ```
- http://localhost:8080/api/certificates with body. If passed tag has no id new tag will be created in database
  ```
  {
    "name" : "swimming pool on nature",
    "description" : "swim swim swim",
    "price" : 3.14,
    "duration" : 14,
    "tags":[{"id":22, "name":"water"}, {"name":"spring"}]
  }
  ```
- http://localhost:8080/api/users with body
  ```
  {
    "name" : "alexa"
  }
  ```
- http://localhost:8080/api/orders with body
  ```
  {
    "user" : {"id" : 3 },
    "giftCertificate" : {"id" : 1 },
    "price" : 3.25
  }
  ```

PATCH (updates passed fields). All except users/
  - path is the same as for findById but with body as for post method. If any field isn't specified it will not be updated

DELETE (delete entry with passed id). All except users/
  - path is the same as for findById. 
  
  
Error handling (wxamples)
- http://localhost:8080/api/tags/222

![image](https://user-images.githubusercontent.com/111181469/235832883-e71a75f9-f3c9-4a2d-9f08-c19350e982a3.png)
- http://localhost:8080/api/tags?page=1&pageSize=no

![image](https://user-images.githubusercontent.com/111181469/235832949-3217df84-e6d3-4924-8d51-1b0598131db2.png)

