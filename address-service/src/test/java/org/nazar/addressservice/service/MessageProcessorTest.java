package org.nazar.addressservice.service;

import org.junit.jupiter.api.Test;
import org.nazar.common.models.AddressClientData;
import org.nazar.common.models.PersonalInfoClientData;
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
    private AddressFetcher addressFetcher;

    @MockBean
    private JmsTemplate jmsTemplate;

    @Test
    void testProcessMessage_success() {
        PersonalInfoClientData incomingData = new PersonalInfoClientData("12345", "Name: John Doe");
        when(addressFetcher.fetchAddress("12345")).thenReturn("Address: 123 Main St");

        messageProcessor.processMessage(incomingData);

        verify(addressFetcher).fetchAddress("12345");
        verify(jmsTemplate).convertAndSend(eq("contacts-queue"), any(AddressClientData.class));
    }
}
