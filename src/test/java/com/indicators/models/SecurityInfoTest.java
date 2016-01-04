package com.indicators.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SecurityInfoTest {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeClass
    public static void setUp() throws Exception {
        MAPPER.findAndRegisterModules();
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void initializesTickValuesAndTradingHoursToEmptySetWhenNull() {
        SecurityInfo securityInfo = new SecurityInfo("TRSE", "MSFT", null, null, null);

        assertThat(securityInfo.getTickValues(), is(Collections.emptySet()));
        assertThat(securityInfo.getTradingHours(), is(Collections.emptySet()));
    }

    @Test
    public void deserializesFromJsonWithUnwrappedInstrument() throws Exception {
        final String marketCode = "XNYS";
        final String securityCode = "MSFT";
            final String jsonSecurityInfo = "{\"tickValues\":[{\"tick\":0.1,\"start\":0.8}]," +
                "\"tradingHours\":[{\"days\":[\"Mon\"],\"closeMinute\":18,\"length\":15}]," +
                "\"marketCode\":\"" + marketCode + "\",\"securityCode\":\"" + securityCode +
                "\",\"tradingSession\":{\"open\":\"2015-10-19T19:22:11.7767845Z\"," +
                "\"close\":\"2015-10-19T19:22:11.7767845Z\"}}";

        final SecurityInfo securityInfo = MAPPER.readValue(jsonSecurityInfo, SecurityInfo.class);

        final Instrument instrument = securityInfo.getInstrument();
        assertThat(instrument.getMarketCode(), is(marketCode));
        assertThat(instrument.getSecurityCode(), is(securityCode));
    }

    @Test
    public void serializesToJsonWithUnwrappedInstrument() throws Exception {
        final SecurityInfo securityInfo = new SecurityInfo("TRSE", "MSFT", null, null, null);

        final String serializedSecurityInfoString = MAPPER.writeValueAsString(securityInfo);

        final JsonNode serializedSecurityInfo = MAPPER.readTree(serializedSecurityInfoString);
        final Instrument instrument = securityInfo.getInstrument();
        assertThat(serializedSecurityInfo.get("marketCode").asText(), is(instrument.getMarketCode()));
        assertThat(serializedSecurityInfo.get("securityCode").asText(), is(instrument.getSecurityCode()));
    }

    @Test
    public void validationProducesConstraintViolationsForInvalidValues() {
        final int expectedNumberOfViolations = 8;
        final Tick invalidTick = new Tick(null, new BigDecimal("0.86"));
        final Set<Tick> invalidTicks = new HashSet<>(Collections.singletonList(invalidTick));
        final TradingHours invalidTradingHours = new TradingHours(null, -50, -50);
        final Set<TradingHours> invalidTradingHoursSet = new HashSet<>(Collections.singletonList(invalidTradingHours));
        final SecurityInfo securityInfo =
                new SecurityInfo(null, null, invalidTicks, invalidTradingHoursSet, new TradingSession(null, null));

        final Set<ConstraintViolation<SecurityInfo>> allConstraintViolations = VALIDATOR.validate(securityInfo);

        assertThat(allConstraintViolations.size(), is(expectedNumberOfViolations));
    }
}
