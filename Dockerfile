# FROM openjdk:11
# VOLUME /tmp
# EXPOSE 8080
# ADD lib /app/lib
# ARG JAR_FILE=target/zatca-spring-api-0.0.1-SNAPSHOT.jar
# COPY ${JAR_FILE} zatca-spring-api.jar
# ENTRYPOINT ["java", "-cp", "/app/lib/zatca-einvoicing-sdk-234-R3.2.0.jar:/app/zatca-spring-api.jar", "com.zatca.sdk.MainApp"]



FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD lib /app/lib
ARG JAR_FILE=target/zatca-spring-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} zatca-spring-api.jar
COPY lib/zatca-einvoicing-sdk-234-R3.2.0/Apps/zatca-einvoicing-sdk-234-R3.2.0.jar /app/lib/zatca-einvoicing-sdk-234-R3.2.0/Apps/zatca-einvoicing-sdk-234-R3.2.0.jar
ENTRYPOINT ["java", "-cp", "/app/lib/zatca-einvoicing-sdk-234-R3.2.0/Apps/zatca-einvoicing-sdk-234-R3.2.0.jar:/app/zatca-spring-api.jar", "com.zatca.sdk.MainApp"]

