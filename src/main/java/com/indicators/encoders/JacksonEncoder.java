package com.indicators.encoders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.atmosphere.config.managed.Encoder;

import javax.inject.Inject;
import java.io.IOException;

public class JacksonEncoder implements Encoder<JacksonEncoder.Encodable, String> {

    @Inject
    private ObjectMapper mapper;

    @Override
    public String encode(Encodable m) {
        try {
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            return mapper.writeValueAsString(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface Encodable {
    }
}
