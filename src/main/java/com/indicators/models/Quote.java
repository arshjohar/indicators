package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

public class Quote {
    @JsonUnwrapped
    @Valid
    private final Instrument instrument;

    @NotNull
    private final BigDecimal bidPrice;
    private final int bidSize;

    @NotNull
    private final BigDecimal askPrice;
    private final int askSize;

    @NotNull
    private final BigDecimal lastPrice;
    private final int lastSize;

    @NotNull
    private final Instant sentAt;

    private Quote() {
        this.instrument = null;
        this.bidPrice = null;
        this.bidSize = 0;
        this.askPrice = null;
        this.askSize = 0;
        this.lastPrice = null;
        this.lastSize = 0;
        this.sentAt = null;
    }

    public Quote(final String marketCode, final String securityCode, final BigDecimal bidPrice, final int bidSize,
                 final BigDecimal askPrice, final int askSize, final BigDecimal lastPrice, final int lastSize,
                 final Instant sentAt) {
        this.instrument = new Instrument(marketCode, securityCode);
        this.bidPrice = bidPrice;
        this.bidSize = bidSize;
        this.askPrice = askPrice;
        this.askSize = askSize;
        this.lastPrice = lastPrice;
        this.lastSize = lastSize;
        this.sentAt = sentAt;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public int getBidSize() {
        return bidSize;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }

    public int getAskSize() {
        return askSize;
    }

    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    public int getLastSize() {
        return lastSize;
    }

    public Instant getSentAt() {
        return sentAt;
    }
}
