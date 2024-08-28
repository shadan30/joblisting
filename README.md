# joblisting project

This is multi-module spring project which supports MongoDB , Redis caching , Monitoring via ELK stack, and has security features

## Requirements
* OpenJDK 21
* SpringBoot 3
* Redis Cache
* ELK stack
* Http Basic authentication


## MongoDB
* **Standalone**
* Import the data from jsondata file in JobPost collection of telusko database in mongoDB for some raw data.
* Import the data from UserDetailsJson file in User collection of telusko database in mongoDB for some raw data.
  * passwords saved -> dem1 -> demo1@1234 ; demo2 -> demo2@1234 ; demo3 -> demo3@1234 ; demo4 -> demo4@123 ; demo5 -> demo5@1234 ; demo6 -> demo6@234
## Architecture

- joblisting-commons -> Contains POJOs and global exception handling.
- joblisting-database -> Data access layer.
- joblisting-core -> Contains configurations, controllers, services, and acts as the application starter
- joblisting-security -> 
    - First, implemented authentication from properties file
    - Second, implemented authentication from database users


## Module dependency
- joblisting-commons -> Not using any base package.
- joblisting-database -> Uses joblisting-commons.
- joblisting-security -> Uses joblisting-database and inherently joblisting-commons
- joblisting-core -> Uses joblisting-security and inherently joblisting-database


## Packages
- The "controller" package holds controllers for API endpoints and provides interception.
- The "dto" package contains Data Transfer Objects used to communicate between different layers
- The "exception" package  has GlobalExceptionHandler used to handle exceptions thrown across your entire application in one place, this package also has "code" object which defines custom exception class , APIException class to encapsulate detailed information about thrown exception, ErrorNames enum to define custom error names.
- The "helper" package has helper classes.
- The "model" package has model classes which defines structure of entities stored in MongoDB
- The "repository" package holds Repository, It contains interfaces and classes that interact with the MongoDB database, allowing for CRUD operations.
- The "service" package holds service classes which defined business logic.
- The "transformer" package contains the mapper, this contains classes and interfaces responsible for converting between different object models.
- The "configuration" package contains the configuration beans,This also has "helper" package to support cache functions manually
  - We have initiated bean of Redis Cache to use Expiration Time of cache entries .
  - We have configured security layer.

