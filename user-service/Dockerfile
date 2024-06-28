# Dockerfile for UserService

# Use Java 17 Corretto as base image
FROM amazoncorretto:17-alpine

# Set working directory
WORKDIR /app

# Copy the packaged JAR file
COPY build/libs/user-service.jar /app/userservice.jar

# Expose port if needed
EXPOSE 8080

# Command to run the service
CMD ["java", "-jar", "/app/userservice.jar"]
