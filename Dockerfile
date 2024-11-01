# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the container
COPY target/your-app-name.jar app.jar

# Expose the application port
EXPOSE 2001

# Command to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]