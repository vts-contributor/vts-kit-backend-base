FROM openjdk:8u201-jdk-alpine
ARG JAR_FILE=target/back-end-template-0.0.1.jar
COPY ${JAR_FILE} /back-end-template-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/back-end-template-0.0.1.jar"]