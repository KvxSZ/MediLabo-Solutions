FROM openjdk:21-slim-bookworm
ARG JAR_FILE=target/*.jar
EXPOSE 8081
COPY ${JAR_FILE} patient.jar
ENTRYPOINT ["java","-jar","/patient.jar"]