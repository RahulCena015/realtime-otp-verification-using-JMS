FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} myapp.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/myapp.jar"]