# Address Service

## Overview
The Address Service manages address information and provides address validation and geocoding services within the Business Process Management System. It handles address-related operations and integrates with mapping services.

## Configuration
- Port: 8082
- ActiveMQ Broker URL: tcp://activemq:61616
- Queue: address-queue

## Development
1. Clone the repository
2. Run `mvn clean install`
3. Start the service using `docker-compose up`
