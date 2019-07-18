#My Retail
####Service Name: myretail-products-api

---
## GET Product Details
### Description
Gets Product Details from Mongo Database and also get the product name from external api.

## HTTP Method
GET

## PUT Product Details
## Description
Updates Product Details in Mongo Database.

## HTTP Method
PUT

##GitHub URL
https://github.com/santoshkandiana/myretail

## Notes to reviewer

Used BDD and TDD for building the application.
Used lombok framework for builders and models.
used gradle instead of maven as i have been using gradle for while
Used Mongo morphia framework to talk to mongo database


##How to run the application on local
Please check out master branch from github.
Please Right click and run MyretailApplication application.
Please Use any tool like postman or ARC to run the GET and PUT operation
Also Additionally i have created a POST operation which would be useful to create records for GET and PUT operations
Please refer swagger documentation

##Code Quality checks
Ran sonarlint for quality check, could not run veracode due to licesnse issues

##Workspace Setup

Please checkout master branch in your favorite editor
This code is tested with Java 8 and Intellij
set up graddle setting to graddle wrapper if local machiene does not have gradle
Right click on MyretailProductApiApplication and click on run to bootstrap the spring boot application
 
 
 