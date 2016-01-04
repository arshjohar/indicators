package com.indicators.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ServerStatusTest {
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @BeforeClass
    public static void setUp() throws Exception {
        MAPPER.findAndRegisterModules();
    }

    @Test
    public void forValueReturnsTheEnumForAString() {
        assertThat(ServerStatus.forValue("connected"), is(ServerStatus.CONNECTED));
        assertThat(ServerStatus.forValue("disconnected"), is(ServerStatus.DISCONNECTED));
    }

    @Test(expected = NoSuchElementException.class)
    public void forValueRaisesExceptionForInvalidStrings() {
        ServerStatus.forValue("invalid_value");
    }
}
