# Bike stats
![Build Status](https://travis-ci.org/MomoTheCat/statsSpring.svg?branch=master)

Application collecting data about bikes in Lodz, Poland.

## SetUp
You need to set MONGOLAB_URI environmental variable to your own database! We use mLab, you can create a free account there.

To easily access the end points we recommend to use POSTMAN, just import the collection: https://www.getpostman.com/collections/3b7d7a0ae84783f18074

You can store data about bikes in your city by changing 'bike.uri' in application.properties to one that suites you most. Find network on: http://api.citybik.es/v2/networks

## Additional
This app use CityBikes API: https://api.citybik.es/v2/


### Polish -  for the purposes of assessment
Używane elementy:

 - Maven
 - Spring boot
 - YAML
 - Lombok
 - Jackson
 - Thymeleaf
 - slf4j.Logger
 - Tomcat
 - MongoDB driver

 - RESTfull api
 - MongoDB
 - Dependency Injection
 - Refleksja (W kodzie i w testach)
 - Builder
 - Interfejs
 - Java 8 Steam
 - Java 8 Lambda Expressions
 - Java 8 Optional
 - Atomic long
 - Executor scheduleAtFixedRate
 - Sort collection

 - jUnit
 - Mockito

 - Travis trigerujacy Heroku
 - Heroku
