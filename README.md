# Jumble-Shop E-commerce Application

## Overview
Jumble-Shop is an e-commerce application built using microservices architecture. It consists of three main services: user-service, product-service, and order-service. 
Each service is responsible for managing specific functionalities of the application as each of the name suggests.

### Technologies Used
- Java 17 (Corretto)
- Spring Boot
- Docker
- Docker Compose
- PostgresSQL
- JWT Authentication

## Setup Instructions

### Prerequisites
1. Java Development Kit (JDK) 17
2. Docker
3. MySQL Database

### Steps to Setup Each Microservice

#### 1. **user-service**

**Clone the Repository**
```bash
git clone https://github.com/vash02/jumble-shop.git
cd user-service 
```

**Build the application**
```bash
./gradlew build```

**Create Docker Image**
```bash
docker build -t user-service```

**Run Docker Container**
```bash
docker run -d -p 8080:8080 user-service
```
#### 2. **product-service**
```bash
cd product-service
```
**Build the application**
```bash
./gradlew build
```

**Create Docker Image**
```bash
docker build -t product-service
```

**Run Docker Container**
```bash
docker run -d -p 8081:8081 product-service
```
### 3. **order-service**
```bash
cd order-service
```
**Build the application**
```bash
./gradlew build
```

**Create Docker Image**
```bash
docker build -t order-service 
```

**Run Docker Container**
```bash
docker run -d -p 8082:8082 order-service
```
### Running the application with Docker Compose

```bash
docker-compose.yml 
```

```bash
version: '3'
services:
  user-service:
    image: user-service
    ports:
      - "8080:8080"
    depends_on:
      - mysql-db

  product-service:
    image: product-service
    ports:
      - "8081:8081"
    depends_on:
      - mysql-db

  order-service:
    image: order-service
    ports:
      - "8082:8082"
    depends_on:
      - mysql-db

  mysql-db:
    image: mysql:latest
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: jumbledb
    ports:
      - "3306:3306"
```

### Running Docker Compose
```bash
docker-compose up
```

## Accessing the Application

- **User Service:** [http://localhost:8080](http://localhost:8080)
- **Product Service:** [http://localhost:8081](http://localhost:8081)
- **Order Service:** [http://localhost:8082](http://localhost:8082)

## Additional Configuration

Each service require specific configurations for database connections, environment variables, etc.
`application.properties`  configuration files can be found under `{service-name}/src/main/resources` directory.

## Handling Concurrency

I have used pessimistic locking for critical sections of code where concurrent access can cause data inconsistency, 
like for order placement, concurrent product table updates, concurrent order status updates. I have used pessimistic locking because,
as the application might not be dealing with high scale concurrent updates as of now, but if the application scales, it'll still ensure data 
consistency and the transactions can get fairly complex because a single request may require updates in mutiple tables, that's why its
still better than Optimistic Locking.

## Clustering and Containerization

I have tested deploying each microservice on 3 containers to ensure high availability.
I have used Docker Swarm or scaling the containers and testing maintainability when containers 
are scaled down or one of them goes down for any service.


## Inter-Service Communication APIs

Used RESTful APIs for inter-service communication between user-service, product-service, and order-service. 
I used JWT authentication and session token management for authorizing requests by users for any operations related to orders 
and each  user will be able to check just their orders after authorization, and consequent requests after login will use jwt token 
for the stored for the session after login for further requests to order service.

## Testing

I have implement concurrency and stress tests for order and product service as both of the tables have high probability of concurrent updates.

For end to end testing of inter service communication and API call functioning and DB querying using Spring Boot tools, I used Postman,
will add the collection to git repository, so that one can try and reproduce the results.

## Notes

- Ensure Docker and Docker Compose are properly configured and running on your system.

