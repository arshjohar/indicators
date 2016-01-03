package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@JsonRootName("quote")
public class Quote {
    @NotNull
    @JsonUnwrapped
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
    private final Date sentAt;

    @JsonCreator
    public Quote(@JsonProperty("marketCode") final String marketCode,
                 @JsonProperty("securityCode") final String securityCode,
                 @JsonProperty("bidPrice") final BigDecimal bidPrice,
                 @JsonProperty("bidSize") int bidSize,
                 @JsonProperty("askPrice") BigDecimal askPrice,
                 @JsonProperty("askSize") int askSize,
                 @JsonProperty("lastPrice") BigDecimal lastPrice,
                 @JsonProperty("lastSize") int lastSize,
                 @JsonProperty("sentAt") Date sentAt) {
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

    public Date getSentAt() {
        return sentAt;
    }
}
