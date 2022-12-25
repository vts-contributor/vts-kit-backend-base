FROM openjdk:8u201-jdk-alpine
ARG JAR_FILE=target/process-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /process-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/process-0.0.1-SNAPSHOT.jar"]