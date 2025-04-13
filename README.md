# Business Process Management System

## Overview
This system is a microservices-based business process management solution built with Spring Boot and ActiveMQ. The system consists of five microservices, each responsible for a specific domain of the business process.

## System Improvements

### Why These Improvements?
- **Circuit Breaker** - Prevents system-wide failures by isolating failing services and providing fallback mechanisms
- **Distributed Tracing** - Enables end-to-end request tracking across microservices for better debugging and monitoring
- **Dead Letter Queues** - Ensures message reliability by handling failed message processing and enabling message recovery

## Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- Docker and Docker Compose
- Git

## System Architecture

### Components
1. **Gateway Service** (Port: 8080)
   - Entry point for all client requests
   - Handles request routing and load balancing
   - Implements API Gateway pattern

2. **Personal Info Service** (Port: 8081)
   - Manages personal information of users
   - Handles user profile data
   - Implements data validation and processing

3. **Address Service** (Port: 8082)
   - Manages address information
   - Handles address validation and formatting
   - Provides address-related operations

4. **Contacts Service** (Port: 8083)
   - Manages contact information
   - Handles communication channels
   - Provides contact management operations

5. **Storage Service** (Port: 8085)
   - Manages data storage
   - Handles file operations
   - Provides data persistence services

### Technology Stack
- **Spring Boot**: Core framework for microservices
- **ActiveMQ**: Message broker for inter-service communication
- **Docker**: Containerization platform
- **JPA**: Data access layer
- **SQLite**: Lightweight database for storage service

### Communication
Services communicate through ActiveMQ message queues:
- personal-info-queue
- address-queue
- contacts-queue
- storage-queue

Each queue has a corresponding Dead Letter Queue (DLQ) for error handling:
- personal-info-queue.DLQ
- address-queue.DLQ
- contacts-queue.DLQ
- storage-queue.DLQ

DLQs are used to:
- Store messages that failed processing
- Enable message recovery and retry
- Prevent message loss
- Provide visibility into processing failures

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd business-process
```

### 2. Build the Project
```bash
# Build all services
mvn clean install

# Or build individual services
cd gateway-service && mvn clean install
cd ../personal-info-service && mvn clean install
cd ../address-service && mvn clean install
cd ../contacts-service && mvn clean install
cd ../storage-service && mvn clean install
```

### 3. Start the System
```bash
# Start all services using Docker Compose
docker-compose up -d

# Check service status
docker-compose ps

# View logs
docker-compose logs -f
```

### 4. Access the Services
- Gateway Service: http://localhost:8080
- Personal Info Service: http://localhost:8081
- Address Service: http://localhost:8082
- Contacts Service: http://localhost:8083
- Storage Service: http://localhost:8085
- ActiveMQ Console: http://localhost:8161 (admin/admin)

### 5. Stop the System
```bash
# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

## Development

### Running Services Individually
Each service can be run independently using Maven:
```bash
# Run gateway service
cd gateway-service
mvn spring-boot:run

# Run personal info service
cd personal-info-service
mvn spring-boot:run

# Run address service
cd address-service
mvn spring-boot:run

# Run contacts service
cd contacts-service
mvn spring-boot:run

# Run storage service
cd storage-service
mvn spring-boot:run
```

### Testing
```bash
# Run tests for all services
mvn test

# Run tests for specific service
cd <service-directory>
mvn test
```

### Debugging
1. Start services in debug mode:
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

## Monitoring and Management
- **Health Checks**: http://localhost:8080/actuator/health
- **Metrics**: http://localhost:8080/actuator/metrics
- **ActiveMQ Console**: http://localhost:8161

### Logs
```bash
# View logs for specific service
docker-compose logs -f <service-name>

# View all logs
docker-compose logs -f
```