package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TradingSession {
    private final Date open;
    private final Date close;

    @JsonCreator
    public TradingSession(@JsonProperty("open") Date open, @JsonProperty("close") Date close) {
        this.open = open;
        this.close = close;
    }

    public Date getOpen() {
        return open;
    }

    public Date getClose() {
        return close;
    }
}
