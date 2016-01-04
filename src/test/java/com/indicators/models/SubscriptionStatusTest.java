package com.indicators.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SubscriptionStatusTest {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeClass
    public static void setUp() throws Exception {
        MAPPER.findAndRegisterModules();
    }

    @Test
    public void deserializesFromJsonWithUnwrappedInstrumentAndValueOfStatusEnum() throws Exception {
        final String marketCode = "XNYS";
        final String securityCode = "MSFT";
        final SubscriptionStatus.Status status = SubscriptionStatus.Status.SUBSCRIBED;
        final String jsonSubscriptionStatus = "{\"marketCode\":\"" + marketCode +
                "\",\"securityCode\":\"" + securityCode + "\",\"status\":\"" + status.toValue() + "\"}";

        final SubscriptionStatus subscriptionStatus =
                MAPPER.readValue(jsonSubscriptionStatus, SubscriptionStatus.class);

        final Instrument instrument = subscriptionStatus.getInstrument();
        assertThat(instrument.getMarketCode(), is(marketCode));
        assertThat(instrument.getSecurityCode(), is(securityCode));
        assertThat(subscriptionStatus.getStatus(), is(status));
    }

    @Test
    public void serializesToJsonWithUnwrappedInstrumentAndValueOfStatusEnum() throws Exception {
        final String marketCode = "XNYS";
        final String securityCode = "MSFT";
        final SubscriptionStatus.Status status = SubscriptionStatus.Status.UNSUBSCRIBED;
        final SubscriptionStatus subscriptionStatus = new SubscriptionStatus(marketCode, securityCode, status);

        final String serializedSubscriptionStatusString = MAPPER.writeValueAsString(subscriptionStatus);

        final JsonNode serializedSubscriptionStatus = MAPPER.readTree(serializedSubscriptionStatusString);
        final Instrument instrument = subscriptionStatus.getInstrument();
        assertThat(serializedSubscriptionStatus.get("marketCode").asText(), is(instrument.getMarketCode()));
        assertThat(serializedSubscriptionStatus.get("securityCode").asText(), is(instrument.getSecurityCode()));
        assertThat(serializedSubscriptionStatus.get("status").asText(), is(subscriptionStatus.getStatus().toValue()));
    }

    @Test
    public void validationProducesConstraintViolationsForNonNullableValues() {
        final int expectedNumberOfViolations = 3;
        final SubscriptionStatus subscriptionStatus = new SubscriptionStatus(null, null, null);

        final Set<ConstraintViolation<SubscriptionStatus>> allConstraintViolations =
                VALIDATOR.validate(subscriptionStatus);
        final Set<ConstraintViolation<SubscriptionStatus>> statusConstraintViolation =
                VALIDATOR.validateProperty(subscriptionStatus, "status");
        final Instrument instrument = subscriptionStatus.getInstrument();
        final Set<ConstraintViolation<Instrument>> marketCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "marketCode");
        final Set<ConstraintViolation<Instrument>> securityCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "securityCode");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(statusConstraintViolation.iterator().next().getMessage(), is("may not be null"));
        assertThat(marketCodeConstraintViolations.iterator().next().getMessage(), is("may not be null"));
        assertThat(securityCodeConstraintViolations.iterator().next().getMessage(), is("may not be empty"));
    }

    @Test
    public void forValueReturnsTheCorrectEnumForAString() {
        assertThat(SubscriptionStatus.Status.forValue("pending"), is(SubscriptionStatus.Status.PENDING));
        assertThat(SubscriptionStatus.Status.forValue("invalid"), is(SubscriptionStatus.Status.INVALID));
        assertThat(SubscriptionStatus.Status.forValue("subscribed"), is(SubscriptionStatus.Status.SUBSCRIBED));
        assertThat(SubscriptionStatus.Status.forValue("unsubscribed"), is(SubscriptionStatus.Status.UNSUBSCRIBED));
    }

    @Test(expected = NoSuchElementException.class)
    public void forValueRaisesExceptionForInvalidStrings() {
        SubscriptionStatus.Status.forValue("invalid_value");
    }
}
