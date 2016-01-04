package com.indicators.models;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

public class Tick {
    @NotNull
    private final BigDecimal tick;
    private final BigDecimal start;

    public Tick(final BigDecimal tick, final BigDecimal start) {
        this.tick = tick;
        this.start = start;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tick tick1 = (Tick) o;

        return tick.equals(tick1.tick) && (start == null ? tick1.start == null : start.equals(tick1.start));
    }

    @Override
    public int hashCode() {
        return Objects.hash(tick, start);
    }

    public BigDecimal getTick() {
        return tick;
    }

    public BigDecimal getStart() {
        return start;
    }
}
