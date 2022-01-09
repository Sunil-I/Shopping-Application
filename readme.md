
# Shopping Application

# Contents
1) [Using the app](#using-the-app)
    - [How to set up](#how-to-setup)
    - [System Requirements](#system-requirements)

2) [Objectives](#objectives)

3) [Requirements](#requirements)

4) [Testing](#testing)

5) [Design](./documentation/design.md)
# Using the app

You should be able to visit the app via http://localhost:8080/ or https://com528-shopping-app.herokuapp.com/

## How to set up

1) Clone the repo
```shell
git clone https://github.com/Sunil-I/COM528-AE2 app
```
2) Install everything
```shell
cd app
mvn clean install
```
3) Run spring / tomcat
```shell
cd web
mvn cargo:run
```
```shell
cd web
mvn spring-boot:run
```
## System Requirements
The system requirements for this program are:
- [Java JDK](https://www.oracle.com/java/technologies/downloads/#java8) version 11 or newer.
- [Apache Maven](https://maven.apache.org/install.html) 3.8 or newer
- [Tomcat 9](https://tomcat.apache.org/download-90.cgi)
- [Google Chrome](https://www.google.co.uk/chrome/) or [Firefox](https://www.mozilla.org/en-GB/firefox/new/)

# Objectives
The objective was to design, develop and test with java tools a shopping cart application which would let the users explore a catalogue allowing them to add items to the basket with the main goal with the user being able to make a transaction which the admin would approve.
# Requirements
The requirements for the application were:
1) The application uses java technology for the back end
2) The application uses frontend technology and jsp for rendering web pages
3) We are required to use a logging framework to help the debugging within our application
4) All classes must be documented in Javadoc
5) It should capture and log all errors
6) Along with helpful error messages for user generated errors

# Testing
To meet the requirements of our application being able to compile in every environment possible we made use of CI/CD to run tests as well as compile our code to ensure that everything works as intended, you can view the builds [here](https://github.com/Sunil-I/COM528-AE2/actions).

Test Plan can be found [here](./documentation/tests.md).
