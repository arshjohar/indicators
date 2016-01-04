package com.indicators.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class InstrumentTest {
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationProducesConstraintViolationsForNonNullableValues() throws Exception {
        final int expectedNumberOfViolations = 2;
        final Instrument instrument = new Instrument(null, null);

        final Set<ConstraintViolation<Instrument>> allConstraintViolations = VALIDATOR.validate(instrument);
        final Set<ConstraintViolation<Instrument>> marketCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "marketCode");
        final Set<ConstraintViolation<Instrument>> securityCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "securityCode");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(marketCodeConstraintViolations.iterator().next().getMessage(), is("may not be null"));
        assertThat(securityCodeConstraintViolations.iterator().next().getMessage(), is("may not be empty"));
    }

    @Test
    public void validationProducesConstraintViolationsForMarketCodesThatDoNotMatchThePatternAndBlankSecurityCodes()
            throws Exception {
        final int expectedNumberOfViolations = 2;
        final Instrument instrument = new Instrument("NYSQL", "");

        final Set<ConstraintViolation<Instrument>> allConstraintViolations = VALIDATOR.validate(instrument);
        final Set<ConstraintViolation<Instrument>> marketCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "marketCode");
        final Set<ConstraintViolation<Instrument>> securityCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "securityCode");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(marketCodeConstraintViolations.iterator().next().getMessage(),
                is("must match \"^[A-Za-z0-9]{4}$\""));
        assertThat(securityCodeConstraintViolations.iterator().next().getMessage(), is("may not be empty"));
    }

    @Test
    public void equalsReturnsTrueWhenTheSameInstanceOfInstrumentsAreCompared() throws Exception {
        final Instrument instrument1 = new Instrument("NYSE", "MSFT");
        final Instrument instrument2 = instrument1;

        assertThat(instrument1, is(equalTo(instrument2)));
    }

    @Test
    public void equalsReturnsFalseWhenComparedToNull() throws Exception {
        final Instrument instrument1 = new Instrument("NYSE", "MSFT");

        assertThat(instrument1, is(not(equalTo(null))));
    }

    @Test
    public void equalsReturnsFalseWhenComparedToAnObjectOfDifferentClass() throws Exception {
        final Instrument instrument1 = new Instrument("NYSE", "MSFT");

        assertThat(instrument1, is(not(equalTo(new Object()))));
    }

    @Test
    public void equalsReturnsFalseForInstrumentsWithDifferentMarketCodes() throws Exception {
        final String securityCode = "MSFT";
        final Instrument instrument1 = new Instrument("NYSE", securityCode);
        final Instrument instrument2 = new Instrument("SFSE", securityCode);

        assertThat(instrument1, is(not(equalTo(instrument2))));
    }

    @Test
    public void equalsReturnsFalseForInstrumentsWithDifferentSecurityCodes() throws Exception {
        final String marketCode = "NYSE";
        final Instrument instrument1 = new Instrument(marketCode, "MSFT");
        final Instrument instrument2 = new Instrument(marketCode, "GOOG");

        assertThat(instrument1, is(not(equalTo(instrument2))));
    }

    @Test
    public void equalsReturnsTrueForInstrumentsWithSameMarketAndSecurityCodes() throws Exception {
        final String marketCode = "NYSE";
        final String securityCode = "MSFT";
        final Instrument instrument1 = new Instrument(marketCode, securityCode);
        final Instrument instrument2 = new Instrument(marketCode, securityCode);

        assertThat(instrument1, is(equalTo(instrument2)));
    }

    @Test
    public void hashCodeReturnsTheSameHashCodeForInstrumentsWithSameMarketAndSecurityCodes() throws Exception {
        final String marketCode = "NYSE";
        final String securityCode = "MSFT";
        final Instrument instrument1 = new Instrument(marketCode, securityCode);
        final Instrument instrument2 = new Instrument(marketCode, securityCode);

        assertThat(instrument1.hashCode(), is(equalTo(instrument2.hashCode())));
    }

    @Test
    public void hashCodeReturnsDifferentHashCodesForInstrumentsWithDifferentMarketCodes() throws Exception {
        final String securityCode = "GOOG";
        final Instrument instrument1 = new Instrument("NYSE", securityCode);
        final Instrument instrument2 = new Instrument("SFSE", securityCode);

        assertThat(instrument1.hashCode(), is(not(equalTo(instrument2.hashCode()))));
    }


    @Test
    public void hashCodeReturnsDifferentHashCodesForInstrumentsWithDifferentSecurityCodes() throws Exception {
        final String marketCode = "NYSE";
        final Instrument instrument1 = new Instrument(marketCode, "MSFT");
        final Instrument instrument2 = new Instrument(marketCode, "GOOG");

        assertThat(instrument1.hashCode(), is(not(equalTo(instrument2.hashCode()))));
    }
}
