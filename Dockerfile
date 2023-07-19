FROM openjdk:11
VOLUME /tmp
EXPOSE 8082

# My Application JAR
COPY target/zatca-spring-api-0.0.1-snapshot.jar lib/zatca-spring-api-0.0.1-snapshot.jar
# Copy the ZATCA SDK Folder
COPY lib/zatca-einvoicing-sdk-234-R3.2.0 lib/zatca-einvoicing-sdk-234-R3.2.0
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
RUN apt-get update && apt-get install -y jq
RUN cd lib/zatca-einvoicing-sdk-234-R3.2.0 && ./install.sh
RUN . ~/.bash-profile && cd lib/zatca-einvoicing-sdk-234-R3.2.0/Data/Input && fatoora -csr -csrconfig csr-config-example-EN.properties

CMD ["java","-jar","lib/zatca-spring-api-0.0.1-snapshot.jar"]
