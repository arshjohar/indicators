package com.indicators.models;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TickTest {
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationProducesConstraintViolationsForNonNullableValues() {
        final int expectedNumberOfViolations = 1;
        final Tick tick = new Tick(null, null);

        final Set<ConstraintViolation<Tick>> allConstraintViolations = VALIDATOR.validate(tick);
        final Set<ConstraintViolation<Tick>> tickConstraintViolations = VALIDATOR.validateProperty(tick, "tick");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(tickConstraintViolations.iterator().next().getMessage(), is("may not be null"));
    }

    @Test
    public void equalsReturnsTrueForTheSameInstancesOfTick() {
        final Tick tick1 = new Tick(new BigDecimal("0.3"), new BigDecimal("1.2"));
        final Tick tick2 = tick1;

        assertThat(tick1, is(equalTo(tick2)));
    }

    @Test
    public void equalsReturnsFalseWhenComparedToNull() {
        final Tick tick = new Tick(new BigDecimal("0.7"), new BigDecimal("9.0"));

        assertThat(tick, is(not(equalTo(null))));
    }

    @Test
    public void equalsReturnsFalseWhenComparedToInstanceOfAnotherClass() {
        final Tick tick = new Tick(new BigDecimal("0.7"), new BigDecimal("9.0"));

        assertThat(tick, is(not(equalTo(new Object()))));
    }

    @Test
    public void equalsReturnsFalseWhenTickValuesAreUnequal() {
        final BigDecimal start = new BigDecimal("9.0");
        final Tick tick1 = new Tick(new BigDecimal("0.7"), start);
        final Tick tick2 = new Tick(new BigDecimal("0.5"), start);

        assertThat(tick1, is(not(equalTo(tick2))));
    }

    @Test
    public void equalsReturnsFalseWhenStartValuesAreUnequal() {
        final BigDecimal tickValue = new BigDecimal("0.1");
        final Tick tick1 = new Tick(tickValue, new BigDecimal("0.8"));
        final Tick tick2 = new Tick(tickValue, new BigDecimal("1.9"));

        assertThat(tick1, is(not(equalTo(tick2))));
    }

    @Test
    public void equalsReturnsFalseWhenBothTickValuesAreEqualButAStartValueIsNull() {
        final BigDecimal tickValue = new BigDecimal("0.3");
        final Tick tick1 = new Tick(tickValue, null);
        final Tick tick2 = new Tick(tickValue, new BigDecimal("1.2"));

        assertThat(tick1, is(not(equalTo(tick2))));
    }

    @Test
    public void equalsReturnsTrueWhenBothTickAndStartValuesAreEqual() {
        final BigDecimal tickValue = new BigDecimal("0.3");
        final BigDecimal start = new BigDecimal("1.2");
        final Tick tick1 = new Tick(tickValue, start);
        final Tick tick2 = new Tick(tickValue, start);

        assertThat(tick1, is(equalTo(tick2)));
    }

    @Test
    public void equalsReturnsTrueWhenTickValuesAreEqualAndStartValuesAreNull() {
        final BigDecimal tickValue = new BigDecimal("0.3");
        final Tick tick1 = new Tick(tickValue, null);
        final Tick tick2 = new Tick(tickValue, null);

        assertThat(tick1, is(equalTo(tick2)));
    }

    @Test
    public void hashCodeReturnsTheSameHashCodeForTicksWithSameTickAndStartValues() {
        final BigDecimal tickValue = new BigDecimal("0.3");
        final BigDecimal start = new BigDecimal("1.2");
        final Tick tick1 = new Tick(tickValue, start);
        final Tick tick2 = new Tick(tickValue, start);

        assertThat(tick1.hashCode(), is(equalTo(tick2.hashCode())));
    }

    @Test
    public void hashCodeReturnsDifferentHashCodesForTicksWithDifferentTickValues() {
        final BigDecimal start = new BigDecimal("9.0");
        final Tick tick1 = new Tick(new BigDecimal("0.7"), start);
        final Tick tick2 = new Tick(new BigDecimal("0.5"), start);

        assertThat(tick1.hashCode(), is(not(equalTo(tick2.hashCode()))));
    }


    @Test
    public void hashCodeReturnsDifferentHashCodesForTicksWithDifferentStartValues() {
        final BigDecimal tickValue = new BigDecimal("0.1");
        final Tick tick1 = new Tick(tickValue, new BigDecimal("0.8"));
        final Tick tick2 = new Tick(tickValue, new BigDecimal("1.9"));

        assertThat(tick1.hashCode(), is(not(equalTo(tick2.hashCode()))));
    }
}
