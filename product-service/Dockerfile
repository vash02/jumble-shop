# Dockerfile for ProductService

# Use Java 17 Corretto as base image
FROM amazoncorretto:17-alpine

# Set working directory
WORKDIR /app

# Copy the packaged JAR file
COPY /Users/vaibhav/my-repos/jumble-shop/product-service/build/libs/product-service.jar /app/productservice.jar

# Expose port if needed
EXPOSE 8082

# Command to run the service
CMD ["java", "-jar", "/app/productservice.jar"]
