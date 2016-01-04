package com.indicators.models;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ErrorTest {
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationProducesConstraintViolationsForNonNullableValues() throws Exception {
        final int expectedNumberOfViolations = 1;
        final Error error = new Error(124, null);

        final Set<ConstraintViolation<Error>> allConstraintViolations = VALIDATOR.validate(error);
        final Set<ConstraintViolation<Error>> messageConstraintViolations =
                VALIDATOR.validateProperty(error, "message");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(messageConstraintViolations.iterator().next().getMessage(), is("may not be null"));
    }
}
