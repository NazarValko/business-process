package org.nazar.contactsservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.nazar.common.models.AddressClientData;
import org.nazar.common.models.ContactsClientData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private static final String STORAGE_QUEUE = "storage-queue";
    private static final String DLQ_STORAGE_QUEUE = "DLQ.storage-queue";

    private final ContactsFetcher contactsFetcher;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "contacts-queue")
    public void processMessage(AddressClientData incomingData) {
        logger.info("Processing clientId: {}", incomingData.clientId());
        String contacts = contactsFetcher.fetchContacts(incomingData.clientId());
        ContactsClientData clientData = new ContactsClientData(
                incomingData.clientId(),
                incomingData.personalInfo(),
                incomingData.address(),
                contacts
        );
        sendToNextQueue(clientData);
    }

    @CircuitBreaker(name = "jmsSender", fallbackMethod = "fallbackSend")
    private void sendToNextQueue(ContactsClientData clientData) {
        logger.info("Sending data for clientId: {} to queue: {}", clientData.clientId(), STORAGE_QUEUE);
        jmsTemplate.convertAndSend(STORAGE_QUEUE, clientData);
        logger.info("Sent data for clientId: {} to queue: {}", clientData.clientId(), STORAGE_QUEUE);
    }

    private void fallbackSend(ContactsClientData clientData, Throwable t) {
        logger.error("Circuit breaker fallback for clientId: {}, error: {}", clientData.clientId(), t.getMessage());
        try {
            jmsTemplate.convertAndSend(DLQ_STORAGE_QUEUE, clientData);
            logger.info("Sent clientId: {} to DLQ: {}", clientData.clientId(), DLQ_STORAGE_QUEUE);
        } catch (Exception e) {
            logger.error("Failed to send clientId: {} to DLQ: {}, error: {}", clientData.clientId(), DLQ_STORAGE_QUEUE, e.getMessage());
        }
    }
}
