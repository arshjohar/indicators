package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Day {
    MONDAY("Mon"),
    TUESDAY("Tue"),
    WEDNESDAY("Wed"),
    THURSDAY("Thu"),
    FRIDAY("Fri"),
    SATURDAY("Sat"),
    SUNDAY("Sun");

    private final String value;

    Day(final String value) {
        this.value = value;
    }

    @JsonCreator
    public static Day forValue(String value) {
        return Arrays.stream(Day.values())
                .filter(day -> day.toValue().equals(value))
                .findFirst()
                .get();
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
