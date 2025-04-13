# Common Module

## Overview
The Common Module provides shared functionality and utilities used across all services in the Business Process Management System. It contains common components, configurations, and interfaces that promote code reuse and maintain consistency throughout the system.

## System Improvements

### 1. Resilience and Fault Tolerance
- **Circuit Breaker Pattern**
  - Implemented using Resilience4j
  - Prevents cascading failures
  - Provides fallback mechanisms
  - Monitors service health

- **Retry Mechanism**
  - Automatic retry for transient failures
  - Configurable retry policies
  - Exponential backoff strategy

- **Bulkhead Pattern**
  - Isolates service instances
  - Prevents resource exhaustion
  - Improves system stability

## Technical Decisions

### Why These Improvements?
- **Circuit Breaker** - Prevents system-wide failures by isolating failing services and providing fallback mechanisms
- **Distributed Tracing** - Enables end-to-end request tracking across microservices for better debugging and monitoring
- **Centralized Logging** - Provides a single point for log aggregation and analysis across all services
- **Caching Strategy** - Improves system performance by reducing database load and response times
- **Container Orchestration** - Simplifies deployment and scaling of microservices while ensuring high availability

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

## Features
- Shared DTOs and models
- Common utilities and helpers
- Shared configurations
- Cross-service interfaces
- Common constants and enums

## Technical Decisions

### Why Common Module?
- **Code Reuse** - Eliminates code duplication across services by centralizing shared functionality
- **Consistency** - Ensures uniform implementation of common features through standardized components
- **Maintainability** - Simplifies updates and bug fixes by having shared code in one place
- **Standardization** - Enforces consistent patterns and practices across all services

### Dependencies
- Spring Boot - Provides the foundation for building production-grade applications
- Spring Data JPA - Simplifies database access and reduces boilerplate code
- ActiveMQ - Enables reliable asynchronous communication between services
- Lombok - Reduces boilerplate code and improves code readability
- ModelMapper - Simplifies object mapping between different layers

## Usage
To use the common module in your service:

1. Add the dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>common</artifactId>
    <version>${project.version}</version>
</dependency>
```

2. Import the required components in your service classes.

## Development
1. Clone the repository
2. Run `mvn clean install`
3. The module will be available for other services to use

## Integration
The common module is used by:
- Address Service
- Contacts Service
- Personal Info Service
- Storage Service
- Gateway Service

## Versioning
- Follows semantic versioning
- Changes should be backward compatible
- Breaking changes require major version updates
