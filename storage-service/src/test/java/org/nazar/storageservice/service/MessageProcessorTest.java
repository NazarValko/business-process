package org.nazar.storageservice.service;

import org.junit.jupiter.api.Test;
import org.nazar.common.models.ContactsClientData;
import org.nazar.storageservice.entity.ClientEntity;
import org.nazar.storageservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class MessageProcessorTest {
    @Autowired
    private MessageProcessor messageProcessor;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    void testProcessMessage_success() {
        ContactsClientData incomingData = new ContactsClientData(
                "12345", "Name: John", "Address: 123 St", "Phone: 123456789"
        );

        messageProcessor.processMessage(incomingData);

        verify(clientRepository).save(argThat(entity ->
                entity.getClientId().equals("12345") &&
                        entity.getPersonalInfo().equals("Name: John") &&
                        entity.getAddress().equals("Address: 123 St") &&
                        entity.getContacts().equals("Phone: 123456789")
        ));
    }
}
