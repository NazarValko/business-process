package org.nazar.gatewayservice.controller;

import org.junit.jupiter.api.Test;
import org.nazar.gatewayservice.service.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageSender messageSender;

    @Test
    void testValidRequest() throws Exception {
        String json = "{\"clientId\": \"12345\"}";

        mockMvc.perform(post("/api/client")
                        .header("sid", "550877874")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Request accepted for client: 12345"));

        verify(messageSender).sendClientId("12345");
    }

    @Test
    void testInvalidSid() throws Exception {
        String json = "{\"clientId\": \"12345\"}";

        mockMvc.perform(post("/api/client")
                        .header("sid", "invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid SID"));

        verifyNoInteractions(messageSender);
    }

    @Test
    void testMissingSid() throws Exception {
        String json = "{\"clientId\": \"12345\"}";

        mockMvc.perform(post("/api/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid SID"));

        verifyNoInteractions(messageSender);
    }
}
