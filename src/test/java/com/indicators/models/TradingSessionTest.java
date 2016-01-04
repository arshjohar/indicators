package com.indicators.models;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TradingSessionTest {
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationProducesConstraintViolationsForNonNullableValues() {
        final int expectedNumberOfViolations = 2;
        final TradingSession tradingSession = new TradingSession(null, null);

        final Set<ConstraintViolation<TradingSession>> allConstraintViolations = VALIDATOR.validate(tradingSession);
        final Set<ConstraintViolation<TradingSession>> openConstraintViolations =
                VALIDATOR.validateProperty(tradingSession, "open");
        final Set<ConstraintViolation<TradingSession>> closeConstraintViolations =
                VALIDATOR.validateProperty(tradingSession, "close");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(openConstraintViolations.iterator().next().getMessage(), is("may not be null"));
        assertThat(closeConstraintViolations.iterator().next().getMessage(), is("may not be null"));
    }
}
