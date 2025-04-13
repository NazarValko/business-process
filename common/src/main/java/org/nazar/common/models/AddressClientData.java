package org.nazar.common.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record AddressClientData(String clientId, String personalInfo, String address) implements Serializable {
    @JsonCreator
    public AddressClientData(@JsonProperty("clientId") String clientId,
                             @JsonProperty("personalInfo") String personalInfo,
                             @JsonProperty("address") String address) {
        this.clientId = clientId;
        this.personalInfo = personalInfo;
        this.address = address;
    }
}
