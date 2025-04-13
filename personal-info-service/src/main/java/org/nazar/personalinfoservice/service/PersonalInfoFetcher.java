package org.nazar.personalinfoservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonalInfoFetcher {
    private static final Logger logger = LoggerFactory.getLogger(PersonalInfoFetcher.class);

    public String fetchPersonalInfo(String clientId) {
        logger.info("Fetching personal info for clientId: {}", clientId);

        return "Name: John Doe, ClientID: " + clientId;
    }
}
