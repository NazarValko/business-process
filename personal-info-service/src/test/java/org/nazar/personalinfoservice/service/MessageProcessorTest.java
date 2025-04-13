package org.nazar.personalinfoservice.service;

import org.junit.jupiter.api.Test;
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
class MessageProcessorTest {

    @Autowired
    private MessageProcessor messageProcessor;

    @MockBean
    private PersonalInfoFetcher personalInfoFetcher;

    @MockBean
    private JmsTemplate jmsTemplate;

    @Test
    void testProcessMessage_success() {
        when(personalInfoFetcher.fetchPersonalInfo("12345")).thenReturn("Name: John Doe, ClientID: 12345");

        messageProcessor.processMessage("12345");

        verify(personalInfoFetcher).fetchPersonalInfo("12345");
        verify(jmsTemplate).convertAndSend(eq("address-queue"), any(PersonalInfoClientData.class));
    }
}
