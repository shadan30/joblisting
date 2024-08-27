# joblisting project

This is multi-module spring project which supports MongoDB , Redis caching , Monitoring via ELK stack, and has security features

## Requirements
* OpenJDK 21
* SpringBoot 3
* Redis Cache
* ELK stack


## MongoDB
* **Standalone**
* Import the data from jsondata file in mongoDB for some raw data.

## Architecture

- joblisting-commons -> Contains POJOs and global exception handling.
- joblisting-database -> Data access layer..
- joblisting-core -> Contains configurations, controllers, services, and acts as the application starter
- joblisting-security -> Security features (yet to be fully defined).


## Module dependency
- joblisting-commons -> Not using any base package.
- joblisting-database -> Uses joblisting-commons.
- joblisting-core -> Uses joblisting-database and inherently joblisting-commons
- joblisting-security -> Dependencies to be decided.


## Packages
- The "controller" package holds controllers for API endpoints and provides interception.
- The "dto" package contains Data Transfer Objects used to communicate between different layers
- The "exception" package  has GlobalExceptionHandler used to handle exceptions thrown across your entire application in one place, this package also has "code" object which defines custom exception class , APIException class to encapsulate detailed information about thrown exception, ErrorNames enum to define custom error names.
- The "helper" package has helper classes.
- The "model" package has model classes which defines structure of entities stored in MongoDB
- The "repository" package holds Repository, It contains interfaces and classes that interact with the MongoDB database, allowing for CRUD operations.
- The "service" package holds service classes which defined business logic.
- The "transformer" package contains the mapper, this contains classes and interfaces responsible for converting between different object models.
- The "configuration" package contains the configuration beans, we have initiated bean of Redis Cache to use Expiration Time of cache entries,This also has "helper" package to support cache functions manually

