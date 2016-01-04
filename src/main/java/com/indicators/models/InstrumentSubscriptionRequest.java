package com.indicators.models;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Set;

public class InstrumentSubscriptionRequest {
    private final String token;

    @Valid
    private final Set<Instrument> subscribe;

    @Valid
    private final Set<Instrument> unsubscribe;

    public InstrumentSubscriptionRequest(final String token, final Set<Instrument> subscribe,
                                         final Set<Instrument> unsubscribe) {
        this.token = token;
        if (subscribe == null) {
            this.subscribe = Collections.emptySet();
        } else {
            this.subscribe = Collections.unmodifiableSet(subscribe);
        }
        if (unsubscribe == null) {
            this.unsubscribe = Collections.emptySet();
        } else {
            this.unsubscribe = Collections.unmodifiableSet(unsubscribe);
        }

    }

    public String getToken() {
        return token;
    }

    public Set<Instrument> getSubscribe() {
        return subscribe;
    }

    public Set<Instrument> getUnsubscribe() {
        return unsubscribe;
    }
}
