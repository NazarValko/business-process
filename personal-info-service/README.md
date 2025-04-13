# Personal Info Service

## Overview
The Personal Info Service is responsible for managing personal information of users in the Business Process Management System. It handles user profile data, implements data validation, and processes personal information requests.

## Configuration
- Port: 8081
- ActiveMQ Broker URL: tcp://activemq:61616
- Queue: personal-info-queue

## Development
1. Clone the repository
2. Run `mvn clean install`
3. Start the service using `docker-compose up`
