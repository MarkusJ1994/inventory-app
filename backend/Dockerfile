#Build artifact
FROM maven:3.9.0-amazoncorretto-19 AS build

WORKDIR /home/app/
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#Copy artifact and run server
FROM openjdk:19-jdk

WORKDIR /root/
ARG JAR_FILE=inventory-app-0.0.1-SNAPSHOT.jar
COPY --from=build /home/app/target/${JAR_FILE} ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080