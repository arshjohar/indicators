package com.indicators.models;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class TradingSession {
    @NotNull
    private final Instant open;

    @NotNull
    private final Instant close;

    public TradingSession(final Instant open, final Instant close) {
        this.open = open;
        this.close = close;
    }

    public Instant getOpen() {
        return open;
    }

    public Instant getClose() {
        return close;
    }
}
