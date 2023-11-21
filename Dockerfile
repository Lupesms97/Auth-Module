#FROM amazoncorretto:17
#VOLUME /tmp
#EXPOSE 8080
#ADD ./target/Back-end-CRM-0.0.1-SNAPSHOT.jar app.jar
#CMD ["java", "-jar", "/app.jar"]
FROM maven:3.8.5-jdk-11 AS build

COPY pom.xml /app/
COPY src /app/src

RUN mvn -f /app/pom.xml clean package

FROM amazoncorretto:17

ARG JAR_FILE=/target/Back-end-CRM-0.0.1-SNAPSHOT.jar

WORKDIR /app

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]


