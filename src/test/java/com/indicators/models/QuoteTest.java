package com.indicators.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import factories.Quotes;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuoteTest {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeClass
    public static void setUp() throws Exception {
        MAPPER.findAndRegisterModules();
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void deserializesFromJsonWithUnwrappedInstrument() throws Exception {
        final String marketCode = "XNYS";
        final String securityCode = "MSFT";
        final String jsonQuote = "{\"askPrice\":47.67,\"askSize\":4600,\"bidPrice\":47.66," +
                "\"bidSize\":8700,\"lastPrice\":47.66,\"lastSize\":100,\"marketCode\":\"" + marketCode +
                "\",\"securityCode\":\"" + securityCode + "\",\"sentAt\":\"2015-10-19T19:22:11.7767845Z\"}";

        final Quote quote = MAPPER.readValue(jsonQuote, Quote.class);

        final Instrument instrument = quote.getInstrument();
        assertThat(instrument.getMarketCode(), is(marketCode));
        assertThat(instrument.getSecurityCode(), is(securityCode));
    }

    @Test
    public void serializesToJsonWithUnwrappedInstrument() throws Exception {
        final Quote quote = Quotes.build();

        final String serializedQuoteString = MAPPER.writeValueAsString(quote);

        final JsonNode serializedQuote = MAPPER.readTree(serializedQuoteString);
        final Instrument instrument = quote.getInstrument();
        assertThat(serializedQuote.get("marketCode").asText(), is(instrument.getMarketCode()));
        assertThat(serializedQuote.get("securityCode").asText(), is(instrument.getSecurityCode()));
    }

    @Test
    public void validationProducesConstraintViolationsForNonNullableValues() {
        final int expectedNumberOfViolations = 6;
        final Quote quote = new Quote(null, null, null, 0, null, 0, null, 0, null);

        final Set<ConstraintViolation<Quote>> allConstraintViolations = VALIDATOR.validate(quote);
        final Set<ConstraintViolation<Quote>> bidPriceConstraintViolation =
                VALIDATOR.validateProperty(quote, "bidPrice");
        final Set<ConstraintViolation<Quote>> askPriceConstraintViolation =
                VALIDATOR.validateProperty(quote, "askPrice");
        final Set<ConstraintViolation<Quote>> lastPriceConstraintViolation =
                VALIDATOR.validateProperty(quote, "lastPrice");
        final Set<ConstraintViolation<Quote>> sentAtConstraintViolation = VALIDATOR.validateProperty(quote, "sentAt");
        final Instrument instrument = quote.getInstrument();
        final Set<ConstraintViolation<Instrument>> marketCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "marketCode");
        final Set<ConstraintViolation<Instrument>> securityCodeConstraintViolations =
                VALIDATOR.validateProperty(instrument, "securityCode");

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
        assertThat(bidPriceConstraintViolation.iterator().next().getMessage(), is("may not be null"));
        assertThat(askPriceConstraintViolation.iterator().next().getMessage(), is("may not be null"));
        assertThat(lastPriceConstraintViolation.iterator().next().getMessage(), is("may not be null"));
        assertThat(sentAtConstraintViolation.iterator().next().getMessage(), is("may not be null"));
        assertThat(marketCodeConstraintViolations.iterator().next().getMessage(), is("may not be null"));
        assertThat(securityCodeConstraintViolations.iterator().next().getMessage(), is("may not be empty"));
    }
}
