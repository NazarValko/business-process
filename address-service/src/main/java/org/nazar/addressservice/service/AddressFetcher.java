package org.nazar.addressservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressFetcher {
    private static final Logger logger = LoggerFactory.getLogger(AddressFetcher.class);

    public String fetchAddress(String clientId) {
        logger.info("Fetching address for clientId: {}", clientId);
        return "Address: 123 Main St, City, Country";
    }
}
