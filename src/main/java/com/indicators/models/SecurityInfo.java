package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.sun.istack.internal.NotNull;

import java.util.List;

@JsonRootName(value = "securityInfo")
public class SecurityInfo {
    @NotNull
    @JsonUnwrapped
    private final Instrument instrument;
    private final List<Tick> tickValues;
    private final List<TradingHours> tradingHours;
    private final TradingSession tradingSession;

    @JsonCreator
    public SecurityInfo(@JsonProperty("marketCode") final String marketCode,
                        @JsonProperty("securityCode") final String securityCode,
                        @JsonProperty("tickValues") final List<Tick> tickValues,
                        @JsonProperty("tradingHours") final List<TradingHours> tradingHours,
                        @JsonProperty("tradingSession") final TradingSession tradingSession) {
        this.instrument = new Instrument(marketCode, securityCode);
        this.tickValues = tickValues;
        this.tradingHours = tradingHours;
        this.tradingSession = tradingSession;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public List<Tick> getTickValues() {
        return tickValues;
    }

    public List<TradingHours> getTradingHours() {
        return tradingHours;
    }

    public TradingSession getTradingSession() {
        return tradingSession;
    }

}
