package com.indicators.decoders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indicators.models.InstrumentSubscriptionRequest;
import org.atmosphere.config.managed.Decoder;

import javax.inject.Inject;
import java.io.IOException;

public class InstrumentSubscriptionRequestDecoder implements Decoder<String, InstrumentSubscriptionRequest> {
    @Inject
    private ObjectMapper mapper;

    @Override
    public InstrumentSubscriptionRequest decode(String s) {
        try {
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            return mapper.readValue(s, InstrumentSubscriptionRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
