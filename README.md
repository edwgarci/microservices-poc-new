# microservices-poc-new
This project is a POC on the basis of latest developments in Spring Boot. All the libraries used are the latest.

The project uses below components :

1. Spring Cloud Config Server
2. Spring Cloud Gateway
3. KeyCloak
4. Spring Cloud Netflix Eureka
5. Spring Cloud Sleuth
6. Spring Cloud Zipkin
7. Spring Cloud Resilience4J
8. Spring Feign Client
9. AMQB Spring Cloud Bus

At the back end, the entire product (I would call so because the project has more than a couple of microservices !) uses Mongo DB which is on Cloud(Atlas DB). The project is a basic
project that has below features :

1. The project's APIs are protected using KeyCloak. It uses open-id as the base and acts as the oauth2 authentication server. This requires to run the KeyCloak server in local.
2. The project has distributed tracing embedded using Zipkin-Sleuth combo (plans to integrate ELK , more after the project builds into a better event driven approach).
3. The project uses spring cloud config server for centralized property management and also common properties are kept in one single file for easier management. 
4. The project uses API Gateway. However, there seems to be issues in the latest version.
5. The project uses feign client to call API which makes sure that we do not use any rest template and there by hardcoding of URLs are avoided (Only GET is coded for, plans ON to code for POST requests as well).
