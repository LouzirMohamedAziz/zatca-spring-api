FROM openjdk:11
VOLUME /tmp
EXPOSE 8082

# My Application JAR
COPY target/zatca-spring-api-0.0.1-snapshot.jar lib/zatca-spring-api-0.0.1-snapshot.jar
# Copy the ZATCA SDK Folder
COPY lib/zatca-einvoicing-sdk-234-R3.2.0 lib/zatca-einvoicing-sdk-234-R3.2.0

# Configure Zatca environnement variables
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt-get update && apt-get install -y jq
RUN cd lib/zatca-einvoicing-sdk-234-R3.2.0 && ./install.sh
RUN . ~/.bash-profile && . ~/.zshrc
RUN . ~/.bash-profile && . ~/.zshrc

# # Copy the pom.xml and any necessary Maven settings files
# COPY pom.xml .
# COPY mvnw .
# COPY .mvn .mvn
# # Install Zatca Dependency for Maven
# RUN ./mvnw install:install-file -Dfile=/lib/zatca-einvoicing-sdk-234-R3.2.0/Apps/zatca-einvoicing-sdk-234-R3.2.0.jar -DgroupId=com.zatca.invoice -DartifactId=zatca-einvoicing-sdk -Dversion=234-R3.2.0 -Dpackaging=jar
# # Fetch dependencies
# RUN ./mvnw dependency:go-offline
# # Copy the rest of the application files
# COPY src src
# # Build the application
# RUN ./mvnw package
# # Copy the ZATCA SDK Folder
# COPY lib/zatca-einvoicing-sdk-234-R3.2.0 lib/zatca-einvoicing-sdk-234-R3.2.0

CMD ["java","-jar","lib/zatca-spring-api-0.0.1-snapshot.jar"]
