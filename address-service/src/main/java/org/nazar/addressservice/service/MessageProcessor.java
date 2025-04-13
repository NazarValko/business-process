package org.nazar.addressservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.nazar.common.models.AddressClientData;
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
    private static final String CONTACTS_QUEUE = "contacts-queue";
    private static final String DLQ_CONTACTS_QUEUE = "DLQ.contacts-queue";

    private final AddressFetcher addressFetcher;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "address-queue")
    public void processMessage(PersonalInfoClientData incomingData) {
        logger.info("Processing clientId: {}", incomingData.clientId());
        String address = addressFetcher.fetchAddress(incomingData.clientId());
        AddressClientData clientData = new AddressClientData(
                incomingData.clientId(),
                incomingData.personalInfo(),
                address
        );
        sendToNextQueue(clientData);
    }

    @CircuitBreaker(name = "jmsSender", fallbackMethod = "fallbackSend")
    private void sendToNextQueue(AddressClientData clientData) {
        logger.info("Sending data for clientId: {} to queue: {}", clientData.clientId(), CONTACTS_QUEUE);
        jmsTemplate.convertAndSend(CONTACTS_QUEUE, clientData);
        logger.info("Sent data for clientId: {} to queue: {}", clientData.clientId(), CONTACTS_QUEUE);
    }

    private void fallbackSend(AddressClientData clientData, Throwable t) {
        logger.error("Circuit breaker fallback for clientId: {}, error: {}", clientData.clientId(), t.getMessage());
        try {
            jmsTemplate.convertAndSend(DLQ_CONTACTS_QUEUE, clientData);
            logger.info("Sent clientId: {} to DLQ: {}", clientData.clientId(), DLQ_CONTACTS_QUEUE);
        } catch (Exception e) {
            logger.error("Failed to send clientId: {} to DLQ: {}, error: {}", clientData.clientId(), DLQ_CONTACTS_QUEUE, e.getMessage());
        }
    }
}
