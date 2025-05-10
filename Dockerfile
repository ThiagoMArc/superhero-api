FROM maven:3.9.4-amazoncorretto-21 AS maven_build
COPY ./ ./
RUN mvn clean package
FROM openjdk:21
VOLUME /tmp
ARG JAR_FILE=target/superhero-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
