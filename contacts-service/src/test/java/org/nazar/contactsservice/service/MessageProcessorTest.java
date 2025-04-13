package org.nazar.contactsservice.service;

import org.junit.jupiter.api.Test;
import org.nazar.common.models.AddressClientData;
import org.nazar.common.models.ContactsClientData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageProcessorTest {
    @Autowired
    private MessageProcessor messageProcessor;

    @MockBean
    private ContactsFetcher contactsFetcher;

    @MockBean
    private JmsTemplate jmsTemplate;

    @Test
    void testProcessMessage_success() {
        AddressClientData incomingData = new AddressClientData("12345", "Name: John Doe", "Address: 123 Main St");
        when(contactsFetcher.fetchContacts("12345")).thenReturn("Phone: +123456789, Email: john.doe@example.com");

        messageProcessor.processMessage(incomingData);

        verify(contactsFetcher).fetchContacts("12345");
        verify(jmsTemplate).convertAndSend(eq("storage-queue"), any(ContactsClientData.class));
    }
}
