# Jumble-Shop E-commerce Application Backend

## Overview
Jumble-Shop is an e-commerce application built using microservices architecture. It consists of three main services: user-service, product-service, and order-service. 
Each service is responsible for managing specific functionalities of the application as each of the name suggests.

### Technologies Used
- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
- ![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
- ![Docker Compose](https://img.shields.io/badge/Docker%20Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
- ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
- ![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white)

## Setup Instructions

### Prerequisites
1. Java Development Kit (JDK) 17
2. Docker
3. PostgreSQL Database

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
version: '3.8'

services:
  user-service:
    build:
      context: .
      dockerfile: ./Dockerfile
    image: user-service:latest
    ports:
      - "8081:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    networks:
      - mynetwork
    deploy:
      replicas: 3

  order-service:
    build:
      context: .
      dockerfile: ./Dockerfile
    image: order-service:latest
    ports:
      - "8082:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    depends_on:
      - user-service
    networks:
      - mynetwork
    deploy:
      replicas: 3

  product-service:
    build:
      context: .
      dockerfile: ./Dockerfile
    image: product-service:latest
    ports:
      - "8083:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    depends_on:
      - order-service
    networks:
      - mynetwork
    deploy:
      replicas: 3

networks:
  mynetwork:
    driver: bridge

```

### Running Docker Compose
```bash
docker-compose up
```

## Accessing the Application

- **User Service:** [http://localhost:8080](http://localhost:8080)
- **Product Service:** [http://localhost:8081](http://localhost:8081)
- **Order Service:** [http://localhost:8082](http://localhost:8082)

## Access Swagger UI

- **User Service:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Product Service:** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
- **Order Service:** [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

## Testing APIs using Swagger UI
1. Open the Swagger UI for the desired service.
2. Authorize by clicking on the "Authorize" button and entering the JWT token.
3. Execute API calls by selecting the desired endpoint, filling in the required parameters, and clicking "Execute".
4. Review the response directly within the Swagger UI to verify the API's behavior.
## Note: 
- Sample Requests can be found with the collection
- JWT can be obtained by hiting login API with login credentials, which need to be creted first using register API for user service.
- With Swagger UI, there is documentation available at the end point with sample API requests.


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
- All 3 services need to be up for inter service calls to work.
- The above instructions are for setting up the DB and spring boot application on local system.
- Ensure Docker and Docker Compose are properly configured and running on your system.
