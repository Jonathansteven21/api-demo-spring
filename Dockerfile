# Use the official Maven image to build the application
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Use the official OpenJDK image to run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set environment variables for the application
ENV SPRING_PROFILES_ACTIVE=prod

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
