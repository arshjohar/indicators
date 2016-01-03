package com.indicators.encoders;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.atmosphere.config.managed.Encoder;

import javax.inject.Inject;
import java.io.IOException;

public class JacksonEncoder implements Encoder<JacksonEncoder.Encodable, String> {

    @Inject
    private ObjectMapper mapper;

    @Override
    public String encode(Encodable m) {
        try {
            return mapper.writeValueAsString(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface Encodable {
    }
}
