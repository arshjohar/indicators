package com.indicators.models;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TradingHoursTest {
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void initializesDaysToAnEmptySetWhenDaysInitializedToNull() {
        final TradingHours tradingHours = new TradingHours(null, 12, 109);

        assertThat(tradingHours.getDays(), is(Collections.emptySet()));
    }

    @Test
    public void validationProducesConstraintViolationsForInvalidValues() {
        final int expectedNumberOfViolations = 3;
        final TradingHours invalidTradingHours = new TradingHours(null, 1500, -100);
        final TradingHours tradingHoursWithNegativeCloseMinute = new TradingHours(null, -98, 101);

        final Set<ConstraintViolation<TradingHours>> allConstraintViolations = VALIDATOR.validate(invalidTradingHours);
        final Set<ConstraintViolation<TradingHours>> daysConstraintViolations =
                VALIDATOR.validateProperty(invalidTradingHours, "days");
        final Set<ConstraintViolation<TradingHours>> closeMinuteConstraintViolations =
                VALIDATOR.validateProperty(invalidTradingHours, "closeMinute");
        final Set<ConstraintViolation<TradingHours>> lengthConstraintViolations =
                VALIDATOR.validateProperty(invalidTradingHours, "length");
        final Set<ConstraintViolation<TradingHours>> negativeCloseMinuteConstraintViolations =
                VALIDATOR.validateProperty(tradingHoursWithNegativeCloseMinute, "closeMinute");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(daysConstraintViolations.iterator().next().getMessage(), is("may not be empty"));
        assertThat(closeMinuteConstraintViolations.iterator().next().getMessage(), is("must be between 0 and 1440"));
        assertThat(lengthConstraintViolations.iterator().next().getMessage(), is("must be greater than or equal to 1"));
        assertThat(negativeCloseMinuteConstraintViolations.iterator().next().getMessage(),
                is("must be between 0 and 1440"));
    }

    @Test
    public void equalsReturnsTrueForTheSameInstancesOfTradingHours() {
        final TradingHours tradingHours1 = new TradingHours(new HashSet<>(Arrays.asList(Day.MONDAY)), 34, 10);
        final TradingHours tradingHours2 = tradingHours1;

        assertThat(tradingHours1, is(equalTo(tradingHours2)));
    }

    @Test
    public void equalsReturnsFalseWhenComparedToNull() {
        final TradingHours tradingHours = new TradingHours(new HashSet<>(Arrays.asList(Day.MONDAY)), 34, 10);

        assertThat(tradingHours, is(not(equalTo(null))));
    }

    @Test
    public void equalsReturnsFalseWhenComparedToInstanceOfAnotherClass() {
        final TradingHours tradingHours = new TradingHours(new HashSet<>(Arrays.asList(Day.MONDAY)), 34, 10);

        assertThat(tradingHours, is(not(equalTo(new Object()))));
    }

    @Test
    public void equalsReturnsFalseWhenCloseMinutesAreUnequal() {
        final int length = 10;
        final HashSet<Day> days = new HashSet<>(Arrays.asList(Day.MONDAY));
        final TradingHours tradingHours1 = new TradingHours(days, 34, length);
        final TradingHours tradingHours2 = new TradingHours(days, 59, length);

        assertThat(tradingHours1, is(not(equalTo(tradingHours2))));
    }

    @Test
    public void equalsReturnsFalseWhenLengthsAreUnequal() {
        final HashSet<Day> days = new HashSet<>(Arrays.asList(Day.MONDAY));
        final int closeMinute = 34;
        final TradingHours tradingHours1 = new TradingHours(days, closeMinute, 10);
        final TradingHours tradingHours2 = new TradingHours(days, closeMinute, 19);

        assertThat(tradingHours1, is(not(equalTo(tradingHours2))));
    }

    @Test
    public void equalsReturnsFalseWhenDaysAreUnequal() {
        final int closeMinute = 34;
        final int length = 10;
        final TradingHours tradingHours1 =
                new TradingHours(new HashSet<>(Arrays.asList(Day.MONDAY)), closeMinute, length);
        final TradingHours tradingHours2 =
                new TradingHours(new HashSet<>(Arrays.asList(Day.THURSDAY)), closeMinute, length);

        assertThat(tradingHours1, is(not(equalTo(tradingHours2))));
    }

    @Test
    public void equalsReturnsTrueWhenAllTheValuesContainedAreEqual() {
        final HashSet<Day> days = new HashSet<>(Arrays.asList(Day.MONDAY));
        final int closeMinute = 34;
        final int length = 10;
        final TradingHours tradingHours1 = new TradingHours(days, closeMinute, length);
        final TradingHours tradingHours2 = new TradingHours(days, closeMinute, length);

        assertThat(tradingHours1, is(equalTo(tradingHours2)));
    }

    @Test
    public void hashCodeReturnsTheSameHashCodeForTradingHoursWithSameValues() {
        final HashSet<Day> days = new HashSet<>(Arrays.asList(Day.MONDAY));
        final int closeMinute = 34;
        final int length = 10;
        final TradingHours tradingHours1 = new TradingHours(days, closeMinute, length);
        final TradingHours tradingHours2 = new TradingHours(days, closeMinute, length);

        assertThat(tradingHours1.hashCode(), is(equalTo(tradingHours2.hashCode())));
    }

    @Test
    public void hashCodeReturnsDifferentHashCodesForTradingHoursWithDifferentCloseMinutes() {
        final int length = 10;
        final HashSet<Day> days = new HashSet<>(Arrays.asList(Day.MONDAY));
        final TradingHours tradingHours1 = new TradingHours(days, 34, length);
        final TradingHours tradingHours2 = new TradingHours(days, 59, length);

        assertThat(tradingHours1.hashCode(), is(not(equalTo(tradingHours2.hashCode()))));
    }


    @Test
    public void hashCodeReturnsDifferentHashCodesForTradingHoursWithDifferentLengths() {
        final HashSet<Day> days = new HashSet<>(Arrays.asList(Day.MONDAY));
        final int closeMinute = 34;
        final TradingHours tradingHours1 = new TradingHours(days, closeMinute, 10);
        final TradingHours tradingHours2 = new TradingHours(days, closeMinute, 19);

        assertThat(tradingHours1.hashCode(), is(not(equalTo(tradingHours2.hashCode()))));
    }

    @Test
    public void hashCodeReturnsDifferentHashCodesForTradingHoursWithDifferentDays() {
        final int closeMinute = 34;
        final int length = 10;
        final TradingHours tradingHours1 =
                new TradingHours(new HashSet<>(Arrays.asList(Day.MONDAY)), closeMinute, length);
        final TradingHours tradingHours2 =
                new TradingHours(new HashSet<>(Arrays.asList(Day.THURSDAY)), closeMinute, length);

        assertThat(tradingHours1.hashCode(), is(not(equalTo(tradingHours2.hashCode()))));
    }
}
