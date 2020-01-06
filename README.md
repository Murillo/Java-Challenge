# Revolut Java Challenge

This system was developed using hexagonal architecture concepts. For that, it was divided into four modules.

* Core: This module has some business classes, such as exceptions, that can be used in all layers of the application;
* Application: This is the core application module, containing the application domains and business rules;
* Repository: This module belongs to the infrastructure layer, connecting the application with the database and providing methods of access to the central layer and other areas of the infrastructure;
* Presentation: This method also belongs to the infrastructure layer, connecting this system with other systems through REST APIs.

## Requirements
* JDK 1.8 +
* Maven 3.5 +

## Steps

Clone this repository using the below command or download it. 
```
git clone https://github.com/Murillo/Java-Challenge.git
```

Access the source code folder.
```
cd Java-Challenge/src
```

Execute the below command to install all dependencies.
```
mvn install
```

Run the java application using the below command.
```
mvn -pl presentation tomee:run
```

## Endpoints

Open the terminal or another software that call web services and execute the below commands: 

This first endpoint will save a new user in the system.

```
curl -d '{ "name":"First Person", "middleName":"Test", "lastName":"Test", "email":"first_person@gmail.com", "phone":"+5551999999999" }' -H "Content-Type: application/json" -X POST http://localhost:8080/revolut/api/customer
```

Execute the below command to save another user in the system. This way, it will be possible to execute transfer money between customers soon.
```
curl -d '{ "name":"Second Person", "lastName":"Test", "email":"second_person@gmail.com", "phone":"+5551999999998" }' -H "Content-Type: application/json" -X POST http://localhost:8080/revolut/api/customer
```

With the below command, it will be possible to see the customers already saved in this system.
```
curl http://localhost:8080/revolut/api/customer
```

To create a new account, change the `[id]` to the id of one of the already registered users. It will be possible that the first user has the ID 1.
```
curl -d '{ "customerCode": [id] }' -H "Content-Type: application/json" -X POST http://localhost:8080/revolut/api/account
```

Execute the same command changing the `[id]` to the id of another user already saved. It will be possible that the second user has the ID 2.
```
curl -d '{ "customerCode": [id] }' -H "Content-Type: application/json" -X POST http://localhost:8080/revolut/api/account
```

With the below command, you will be able to check all accounts already saved in the system.
```
curl http://localhost:8080/revolut/api/account/all
```

The next command will allow depositing money to a specific account.
```
curl -X PUT http://localhost:8080/revolut/api/account/[id]/deposit?quantity=150
```

The next command will allow withdrawing money from a specific account.
```
curl -X PUT http://localhost:8080/revolut/api/account/[id]/withdraw?quantity=50
```

The last endpoint will allow transferring money between accounts.
```
curl -d '{ "toAccountCode":[id], "value": 100 }' -H "Content-Type: application/json" -X PUT http://localhost:8080/revolut/api/account/[id]/transfer
```

## Other contents
* [SOLID](https://github.com/Murillo/SOLID-Principles)
* [Specification Pattern](https://github.com/Murillo/Specification-Pattern)