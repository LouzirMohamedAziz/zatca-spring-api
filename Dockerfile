# Use the base image
FROM openjdk:11

# Set the working directory within the container
WORKDIR /app

# Copy your application JAR and the ZATCA SDK Folder into the image
COPY target/zatca-spring-api-0.0.1-snapshot.jar /app/target/zatca-spring-api-0.0.1-snapshot.jar
COPY lib/zatca-einvoicing-sdk-234-R3.2.0 /app/lib/zatca-einvoicing-sdk-234-R3.2.0

# Install required tools and dependencies
RUN apt-get update && apt-get install -y jq
RUN chmod +x /app/target/zatca-spring-api-0.0.1-snapshot.jar
RUN chmod +x /app/lib/zatca-einvoicing-sdk-234-R3.2.0

# Configure Zatca environment variables (if needed)
# Note: If you need to set environment variables, consider using ENV instructions here.

# Define the command to run your application
CMD ["sh", "-c", "java -jar /app/target/zatca-spring-api-0.0.1-snapshot.jar > /app/target/invoice_log.txt 2>&1"]
