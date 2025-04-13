package org.nazar.common.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record PersonalInfoClientData(String clientId, String personalInfo) implements Serializable {
    @JsonCreator
    public PersonalInfoClientData(@JsonProperty("clientId") String clientId,
                                  @JsonProperty("personalInfo") String personalInfo) {
        this.clientId = clientId;
        this.personalInfo = personalInfo;
    }
}
