package org.nazar.gatewayservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private MessageSender messageSender;

    @Test
    void sendClientId_success_sendsMessage() {
        String clientId = "12345";
        messageSender.sendClientId(clientId);
        verify(jmsTemplate).convertAndSend(eq("personal-info-queue"), eq("12345"));
    }
}
