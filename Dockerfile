FROM openjdk:17
EXPOSE 8080
ADD /target/gateway-0.0.1-SNAPSHOT.jar docker-client.jar
ENTRYPOINT ["java","-jar","docker-client.jar"]
