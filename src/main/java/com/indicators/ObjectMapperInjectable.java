package com.indicators;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.atmosphere.cpr.AtmosphereConfig;
import org.atmosphere.inject.Injectable;

import java.lang.reflect.Type;

public class ObjectMapperInjectable implements Injectable<ObjectMapper> {

    private final ObjectMapper mapper =
            new ObjectMapper()
                    .findAndRegisterModules()
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

    @Override
    public boolean supportedType(Type t) {
        return (t instanceof Class) && ObjectMapper.class.equals(t);
    }

    @Override
    public ObjectMapper injectable(AtmosphereConfig config) {
        return mapper;
    }
}
