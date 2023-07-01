FROM openjdk:11
VOLUME /tmp
EXPOSE 8082

# My Application JAR
ARG JAR_FILE=target/zatca-spring-api-0.0.1-snapshot.jar
COPY ${JAR_FILE} /app/zatca-spring-api-0.0.1-snapshot.jar

# Copy the ZATCA SDK JAR from local Maven repository
COPY /lib/zatca-einvoicing-sdk-234-R3.2.0/Apps/zatca-einvoicing-sdk-234-R3.2.0.jar /app/zatca-einvoicing-sdk-234-R3.2.0.jar
ENTRYPOINT ["java","-jar","/zatca-spring-api-0.0.1-snapshot.jar"]
