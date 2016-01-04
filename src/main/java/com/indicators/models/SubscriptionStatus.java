package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class SubscriptionStatus {
    @Valid
    @JsonUnwrapped
    private final Instrument instrument;

    @NotNull
    private final Status status;

    private SubscriptionStatus() {
        instrument = null;
        status = null;
    }

    public SubscriptionStatus(final String marketCode, final String securityCode, final Status status) {
        this.instrument = new Instrument(marketCode, securityCode);
        this.status = status;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Status getStatus() {
        return status;
    }

    protected enum Status {
        PENDING("pending"),
        INVALID("invalid"),
        SUBSCRIBED("subscribed"),
        UNSUBSCRIBED("unsubscribed");

        private final String value;

        Status(final String value) {
            this.value = value;
        }

        @JsonCreator
        public static Status forValue(String value) {
            return Arrays.stream(Status.values())
                    .filter(status -> status.toValue().equals(value))
                    .findFirst()
                    .get();
        }

        @JsonValue
        public String toValue() {
            return value;
        }
    }

}
