package org.nazar.storageservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.nazar.common.models.ContactsClientData;
import org.nazar.storageservice.entity.ClientEntity;
import org.nazar.storageservice.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
    private static final String DLQ_STORAGE_QUEUE = "DLQ.storage-queue";
    private final ClientRepository clientRepository;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "storage-queue")
    @Transactional
    public void processMessage(ContactsClientData incomingData) {
        logger.info("Processing clientId: {}", incomingData.clientId());
        save(incomingData);
        logger.info("Processed clientId: {}", incomingData.clientId());
    }

    @CircuitBreaker(name = "dbOperation", fallbackMethod = "fallbackSave")
    private void save(ContactsClientData incomingData) {
        ClientEntity entity = new ClientEntity(
                incomingData.clientId(),
                incomingData.personalInfo(),
                incomingData.address(),
                incomingData.contacts()
        );
        clientRepository.save(entity);
    }

    private void fallbackSave(ContactsClientData incomingData, Throwable t) {
        logger.error("Circuit breaker fallback for saving clientId: {}, error: {}", incomingData.clientId(), t.getMessage());
        try {
            jmsTemplate.convertAndSend(DLQ_STORAGE_QUEUE, incomingData);
            logger.info("Sent clientId: {} to DLQ: {}", incomingData.clientId(), DLQ_STORAGE_QUEUE);
        } catch (Exception e) {
            logger.error("Failed to send clientId: {} to DLQ: {}, error: {}", incomingData.clientId(), DLQ_STORAGE_QUEUE, e.getMessage());
        }
    }
}
