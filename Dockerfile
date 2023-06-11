FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/zatca-spring-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} zatca-spring-api.jar
ENTRYPOINT ["java","-jar","/zatca-spring-api.jar"]
