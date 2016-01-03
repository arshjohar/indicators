package com.indicators.models;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class Tick {
    @NotNull
    private BigDecimal tick;
    @NotNull
    private BigDecimal start;

    public BigDecimal getTick() {
        return tick;
    }

    public void setTick(BigDecimal tick) {
        this.tick = tick;
    }

    public BigDecimal getStart() {
        return start;
    }

    public void setStart(BigDecimal start) {
        this.start = start;
    }
}
