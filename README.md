# TheGourmetCookbook
The Gourmets - Section 003 - Spring 2019

# Description
This repository houses the Spring 2019 Application Programming project for The Gourmets. During the course of the semester our team collaborated to design a javafx desktop application. 

# Motivation
The Gourmet Cookbook finds recipes based on filters to suit your personal preferences. Search criteria is flexible and includes many options such as dietary restrictions or ingredients on hand. Users can build a profile to further customize searches and bookmark a favorite recipe. With a friendly user interface this app is perfect for beginner chefs ready to prepare a delicious home made meal. 

# Installation
Clone the repository to Eclipse, and import the project. Open src/application/main.java and run.

# API Reference and Libraries
This application is powered by the Spoonacular API.

https://spoonacular.com/food-api
https://rapidapi.com/spoonacular/api/recipe-food-nutrition

To connect to the Spoonacular API we utilized Unirest for Java, a set of lightweight HTTP libraries.

http://unirest.io/java.html

Several JAR files are required for unirest requests:
unirest-java-1.4.9
org.json
httpclient 4.3.6
httpmime 4.3.6
httpasyncclient 4.0.2

To unpack the data returned from the Spoonacular Unirest request into java classes we utilized Jackson objectmapping.
jackson-annotations
jackson-core-2.9.8
jackson-databind-2.9.8

# Contributors
Chris Aguirre, Phuong Huynh, Harrison Luko, Ray Sayontani, Marco Zamora