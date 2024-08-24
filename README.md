# joblisting project

## Requirements
OpenJDK 21
SpringBoot 3


MongoDB
* **Standalone**
* Import the data from jsondata file in mongoDB

## Architecture
- The "controller" package holds controllers for API endpoints and provides interception.
- The "dto" package contains Data Transfer Objects used to communicate between different layers
- The "exception" package  has GLobalExceptionHandler used to handle exceptions thrown across your entire application in one place, this package also has "code" object which defines custom exception class , APIException class to encapsulate detailed information about thrown exception, ErroNames enum to define custom error names.
- The "helper" package has helper classes.
- The "model" package has model classes which defines structire of entities stored in MongoDB
- The "repository" package holds Repository, It contains interfaces and classes that interact with the MongoDB database, allowing for CRUD operations.
- The "service" package holds service classes which defined business logic.
- The "tranformer" package contains the mapper, this contains classes and interfaces responsible for converting between different object models.
