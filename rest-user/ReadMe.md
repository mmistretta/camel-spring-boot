# Rest DSL User Demo 

Manual testing may be performed while running the application. The following operations are supported:

GET http://localhost:8080/camel/user/

GET http://localhost:8080/camel/user/{id}

POST http://localhost:8080/camel/user/
With body: 
`{"id":2,
 "username":"joe"
}`

PUT http://localhost:8080/camel/user/
With body: 
`{"id":2,
 "username":"john"
}`

DELETE http://localhost:8080/camel/user/{id}