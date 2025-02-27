# Use a base image with Java
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the build artifact from the target directory to the container
COPY build/libs/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
