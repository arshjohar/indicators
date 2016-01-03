package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class InstrumentSubscriptionStatus {
    @JsonUnwrapped
    private final Instrument instrument;
    private final Status status;

    @JsonCreator
    public InstrumentSubscriptionStatus(@JsonProperty("marketCode") String marketCode,
                                        @JsonProperty("securityCode") String securityCode,
                                        @JsonProperty("status") Status status) {
        this.instrument = new Instrument(marketCode, securityCode);
        this.status = status;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        @JsonProperty("pending")
        PENDING,
        @JsonProperty("invalid")
        INVALID,
        @JsonProperty("subscribed")
        SUBSCRIBED,
        @JsonProperty("unsubscribed")
        UNSUBSCRIBED
    }

}
