package org.nazar.contactsservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContactsFetcher {
    private static final Logger logger = LoggerFactory.getLogger(ContactsFetcher.class);

    public String fetchContacts(String clientId) {
        logger.info("Fetching contacts for clientId: {}", clientId);

        return "Phone: +123456789, Email: john.doe@example.com";
    }
}
