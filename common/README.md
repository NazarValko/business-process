# Common Module

## Overview
The Common Module provides shared functionality and utilities used across all services in the Business Process Management System. It contains common components, configurations, and interfaces that promote code reuse and maintain consistency throughout the system.

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
