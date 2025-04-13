package org.nazar.personalinfoservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.nazar.common.models.PersonalInfoClientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private static final String ADDRESS_QUEUE = "address-queue";
    private static final String DLQ_ADDRESS_QUEUE = "DLQ.address-queue";

    private final PersonalInfoFetcher personalInfoFetcher;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "personal-info-queue")
    public void processMessage(String clientId) {
        logger.info("Processing clientId: {}", clientId);
        String personalInfo = personalInfoFetcher.fetchPersonalInfo(clientId);
        PersonalInfoClientData clientData = new PersonalInfoClientData(clientId, personalInfo);
        sendToNextQueue(clientData);
    }

    @CircuitBreaker(name = "jmsSender", fallbackMethod = "fallbackSend")
    private void sendToNextQueue(PersonalInfoClientData clientData) {
        logger.info("Sending data for clientId: {} to queue: {}", clientData.clientId(), ADDRESS_QUEUE);
        jmsTemplate.convertAndSend(ADDRESS_QUEUE, clientData);
        logger.info("Sent data for clientId: {} to queue: {}", clientData.clientId(), ADDRESS_QUEUE);
    }

    private void fallbackSend(PersonalInfoClientData clientData, Throwable t) {
        logger.error("Circuit breaker fallback for clientId: {}, error: {}", clientData.clientId(), t.getMessage());
        try {
            jmsTemplate.convertAndSend(DLQ_ADDRESS_QUEUE, clientData);
            logger.info("Sent clientId: {} to DLQ: {}", clientData.clientId(), DLQ_ADDRESS_QUEUE);
        } catch (Exception e) {
            logger.error("Failed to send clientId: {} to DLQ: {}, error: {}", clientData.clientId(), DLQ_ADDRESS_QUEUE, e.getMessage());
        }
    }
}
