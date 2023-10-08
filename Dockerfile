FROM maven:3.9.4-amazoncorretto-21 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package
FROM openjdk:21
VOLUME /tmp
ARG JAR_FILE=target/superhero-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
