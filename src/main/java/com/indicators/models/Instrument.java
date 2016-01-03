package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Instrument {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{4}$")
    private final String marketCode;
    @NotNull
    private final String securityCode;

    @JsonCreator
    public Instrument(@JsonProperty("marketCode") final String marketCode,
                      @JsonProperty("securityCode") final String securityCode) {
        this.marketCode = marketCode;
        this.securityCode = securityCode;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

}
