package org.nazar.common.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record ContactsClientData(String clientId, String personalInfo, String address, String contacts) implements Serializable {
    @JsonCreator
    public ContactsClientData(@JsonProperty("clientId") String clientId,
                              @JsonProperty("personalInfo") String personalInfo,
                              @JsonProperty("address") String address,
                              @JsonProperty("contacts") String contacts) {
        this.clientId = clientId;
        this.personalInfo = personalInfo;
        this.address = address;
        this.contacts = contacts;
    }
}
