# Reddclone Micro Services

## Table of contents

- [Technologies Used](#technologies-used)
- [Approach & Planning](#approach& planning)
- [User Stories](#user-stories)
- [Microservices Architecture](#Microservices Architecture)
- [Timeline](#timeline)






## Technologies Used
* User stories [Pivotal Tracker](https://www.pivotaltracker.com/n/projects/2416889)
* Spring
* Hibernate
* Junit
* Eureka
* Docker
* Hystrix

* RestTemplate
* Draw.io for Microservices Architecture diagram
* Pivotal Cloud Foundry (hosting)



## Approach & Planning
We started with writing all user stories applicable to the 3 microservices. We devided them into 3 Epics: "User Auth", "Post CRUD" and "Comment CRUD". From there we wrote and evaluated each single story which ties to a feature in each of the services/Epics. Afterwards we started planning and organizing the project. Since we had to have 3 separate services plus the Api-gateway and Eureka we decided on creating 3 databases- UsersDB, PostsDb and CommentsDB, to keep the micorservices as separate as possible so that they execute their own purpose only. We constructed the skeleton of the project- one main directory with all 5 microservices in it. We configured Eureka first followed by the Api-gateway. Once these were up and running we started building User, Post and Comment microservices. Auth was our next step. We decided to have auth in the Api-gateway which will diretly communicate with the usersDb where ... We build and connected all Apis needed for post and comment CRUD. We used RestTemplate in Post Microservice for it to be able to communicate with Comment Microservice for the two methods where it was needed: Cascade delete all comments that belong to a post,when that post is deleted; Show all comments that belong to a post. Tested all Apis and connected to the front end. 

## Microservices Architecture

## Challenge - API-gateway exits with code 137 on Hristina's computer
deployment and any configurations with it 
creating different databases for each microservice 
making a design decision to make different databases 
Security thu api gateway 

<img src="">
