FROM openjdk:11
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=target/zatca-spring-api-0.0.1.jar
COPY ${JAR_FILE} zatca-spring-api-0.0.1.jar
ENTRYPOINT ["java","-jar","/zatca-spring-api-0.0.1.jar"]