FROM 10.254.144.152/docker.io/library/openjdk:8u201-jdk-alpine
ARG JAR_FILE=target/vts-kit-backend-base-0.0.1.jar
COPY ${JAR_FILE} /vts-kit-backend-base-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/vts-kit-backend-base-0.0.1.jar"]