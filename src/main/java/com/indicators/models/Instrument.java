package com.indicators.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

public class Instrument {
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{4}$")
    private final String marketCode;

    @NotEmpty
    private final String securityCode;

    public Instrument(final String marketCode, final String securityCode) {
        this.marketCode = marketCode;
        this.securityCode = securityCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Instrument that = (Instrument) o;

        return marketCode.equals(that.marketCode) && securityCode.equals(that.securityCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketCode, securityCode);
    }

    public String getMarketCode() {
        return marketCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

}
