package com.indicators.models;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InstrumentSubscriptionRequestTest {
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void initializesSubscribeAndUnsubscribeToEmptySetsIfNull() throws Exception {
        final InstrumentSubscriptionRequest instrumentSubscriptionRequest =
                new InstrumentSubscriptionRequest("token", null, null);

        assertThat(instrumentSubscriptionRequest.getSubscribe(), is(Collections.emptySet()));
        assertThat(instrumentSubscriptionRequest.getUnsubscribe(), is(Collections.emptySet()));
    }

    @Test
    public void validationProducesConstraintViolationsForInvalidInstrumentsInSubscribeAndUnsubscribeSets()
            throws Exception {
        final int expectedNumberOfViolations = 4;
        final Instrument invalidInstrument1 = new Instrument(null, null);
        final Set<Instrument> subscribe = new HashSet<>(Collections.singletonList(invalidInstrument1));
        final Instrument invalidInstrument2 = new Instrument(null, null);
        final Set<Instrument> unsubscribe = new HashSet<>(Collections.singletonList(invalidInstrument2));
        final InstrumentSubscriptionRequest instrumentSubscriptionRequest =
                new InstrumentSubscriptionRequest("token", subscribe, unsubscribe);

        final Set<ConstraintViolation<InstrumentSubscriptionRequest>> allConstraintViolations =
                VALIDATOR.validate(instrumentSubscriptionRequest);

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
    }

}
