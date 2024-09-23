# Use a base image that includes Java
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build artifact (the JAR file) into the container
COPY target/api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on (default for Spring Boot)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
