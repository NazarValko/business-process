package org.nazar.gatewayservice.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.nazar.common.models.ClientRequest;
import org.nazar.common.validators.SidValidator;
import org.nazar.gatewayservice.service.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final MessageSender messageSender;

    @PostMapping("/client")
    public ResponseEntity<String> processClient(@RequestBody ClientRequest request,
                                                @RequestHeader(value = "sid", required = false) String sid) {
        if (!SidValidator.isValidSid(sid)) {
            logger.warn("Invalid SID received: {}", sid);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid SID");
        }

        messageSender.sendClientId(request.clientId());
        return ResponseEntity.ok("Request accepted for client: " + request.clientId());
    }

}
