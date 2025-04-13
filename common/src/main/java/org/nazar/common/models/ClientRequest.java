package org.nazar.common.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record ClientRequest(String clientId) implements Serializable {
    @JsonCreator
    public ClientRequest(@JsonProperty("clientId") String clientId) {
        this.clientId = clientId;
    }
}
