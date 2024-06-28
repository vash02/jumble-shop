# Dockerfile for OrderService

# Use Java 17 Corretto as base image
FROM amazoncorretto:17-alpine

# Set working directory
WORKDIR /app

# Copy the packaged JAR file
COPY build/libs/order-service.jar /app/orderservice.jar

# Expose port if needed
EXPOSE 8081

# Command to run the service
CMD ["java", "-jar", "/app/orderservice.jar"]
