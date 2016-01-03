package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public class InstrumentSubscriptionRequest {
    @NotNull
    private final String token;
    @NotNull
    private final List<Instrument> subscribe;
    @NotNull
    private final List<Instrument> unsubscribe;

    @JsonCreator
    public InstrumentSubscriptionRequest(@JsonProperty("token") final String token,
                                         @JsonProperty("subscribe") final List<Instrument> subscribe,
                                         @JsonProperty("unsubscribe") final List<Instrument> unsubscribe) {
        this.token = token;
        if (subscribe == null) {
            this.subscribe = Collections.emptyList();
        } else {
            this.subscribe = subscribe;
        }
        if (unsubscribe == null) {
            this.unsubscribe = Collections.emptyList();
        } else {
            this.unsubscribe = unsubscribe;
        }

    }

    public String getToken() {
        return token;
    }

    public List<Instrument> getSubscribe() {
        return subscribe;
    }

    public List<Instrument> getUnsubscribe() {
        return unsubscribe;
    }
}
