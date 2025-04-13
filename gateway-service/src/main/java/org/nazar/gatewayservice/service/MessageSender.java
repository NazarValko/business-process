package org.nazar.gatewayservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private static final String PERSONAL_INFO_QUEUE = "personal-info-queue";
    private static final String DLQ_PERSONAL_INFO_QUEUE = "DLQ.personal-info-queue";

    private final JmsTemplate jmsTemplate;

    @CircuitBreaker(name = "jmsSender", fallbackMethod = "fallbackSend")
    public void sendClientId(String clientId) {
        logger.info("Sending clientId: {} to queue: {}", clientId, PERSONAL_INFO_QUEUE);
        jmsTemplate.convertAndSend(PERSONAL_INFO_QUEUE, clientId);
        logger.info("Sent clientId: {} to queue: {}", clientId, PERSONAL_INFO_QUEUE);
    }

    public void fallbackSend(String clientId, Throwable t) {
        logger.error("Circuit breaker fallback for clientId: {}, error: {}", clientId, t.getMessage());
        try {
            jmsTemplate.convertAndSend(DLQ_PERSONAL_INFO_QUEUE, clientId);
            logger.info("Sent clientId: {} to DLQ: {}", clientId, DLQ_PERSONAL_INFO_QUEUE);
        } catch (Exception e) {
            logger.error("Failed to send clientId: {} to DLQ: {}, error: {}", clientId, DLQ_PERSONAL_INFO_QUEUE, e.getMessage());
        }
    }
}
