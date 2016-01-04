package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

public class SecurityInfo {
    @Valid
    @NotNull
    @JsonUnwrapped
    private final Instrument instrument;

    @Valid
    private final Set<Tick> tickValues;

    @Valid
    private final Set<TradingHours> tradingHours;

    @Valid
    private final TradingSession tradingSession;

    private SecurityInfo() {
        this.instrument = null;
        this.tickValues = null;
        this.tradingHours = null;
        this.tradingSession = null;
    }

    public SecurityInfo(final String marketCode, final String securityCode, final Set<Tick> tickValues,
                        final Set<TradingHours> tradingHours, final TradingSession tradingSession) {
        this.instrument = new Instrument(marketCode, securityCode);
        if (tickValues == null) {
            this.tickValues = Collections.emptySet();
        } else {
            this.tickValues = Collections.unmodifiableSet(tickValues);
        }
        if (tradingHours == null) {
            this.tradingHours = Collections.emptySet();
        } else {
            this.tradingHours = Collections.unmodifiableSet(tradingHours);
        }
        this.tradingSession = tradingSession;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Set<Tick> getTickValues() {
        return tickValues;
    }

    public Set<TradingHours> getTradingHours() {
        return tradingHours;
    }

    public TradingSession getTradingSession() {
        return tradingSession;
    }

}
