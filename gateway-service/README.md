# Gateway Service

## Overview
The Gateway Service serves as the entry point for all client requests in the Business Process Management System. It implements the API Gateway pattern and handles request routing, load balancing, and request/response transformation.


## Configuration
- Port: 8080
- ActiveMQ Broker URL: tcp://activemq:61616
- Circuit Breaker:
  - Failure rate threshold: 50%
  - Wait duration: 5000ms
  - Permitted calls: 10
- Rate Limiter:
  - Limit refresh period: 1s
  - Limit for period: 100
- Cache:
  - TTL: 300s
  - Maximum size: 1000

## API Endpoints
- `/api/client/*` - Save Client Data

## Development
1. Clone the repository
2. Run `mvn clean install`
3. Start the service using `docker-compose up`

## Monitoring
- Health Checks: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics
- Circuit Breaker: http://localhost:8080/actuator/circuitbreakers
- Rate Limiter: http://localhost:8080/actuator/ratelimiters 