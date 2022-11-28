FROM openjdk:8u201-jdk-alpine
ARG JAR_FILE=target/vts-kit-backend-base-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /vts-kit-backend-base-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/vts-kit-backend-base-0.0.1-SNAPSHOT.jar"]
