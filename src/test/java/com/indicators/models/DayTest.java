package com.indicators.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DayTest {
    private final static ObjectMapper MAPPER = new ObjectMapper();

    @BeforeClass
    public static void setUp() throws Exception {
        MAPPER.findAndRegisterModules();
    }

    @Test
    public void forValueReturnsTheCorrectEnumForAString() throws Exception {
        assertThat(Day.forValue("Mon"), is(Day.MONDAY));
        assertThat(Day.forValue("Tue"), is(Day.TUESDAY));
        assertThat(Day.forValue("Wed"), is(Day.WEDNESDAY));
        assertThat(Day.forValue("Thu"), is(Day.THURSDAY));
        assertThat(Day.forValue("Fri"), is(Day.FRIDAY));
        assertThat(Day.forValue("Sat"), is(Day.SATURDAY));
        assertThat(Day.forValue("Sun"), is(Day.SUNDAY));
    }

    @Test(expected = NoSuchElementException.class)
    public void forValueRaisesExceptionForInvalidStrings() throws Exception {
        Day.forValue("invalid_value");
    }

    @Test
    public void serializesToJsonUsingTheValue() throws Exception {
        assertThat(MAPPER.writeValueAsString(Day.MONDAY), is("\"Mon\""));
    }

    @Test
    public void deserializesFromJsonUsingTheStringValue() throws Exception {
        assertThat(MAPPER.readValue("\"Sun\"", Day.class), is(Day.SUNDAY));
    }
}
