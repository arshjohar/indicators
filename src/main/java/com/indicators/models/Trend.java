package com.indicators.models;

import java.math.BigDecimal;
import java.time.Instant;

public class Trend {
    private final BigDecimal value;
    private final Instant time;

    public Trend(final BigDecimal value, final Instant time) {
        this.value = value;
        this.time = time;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Instant getTime() {
        return time;
    }
}
