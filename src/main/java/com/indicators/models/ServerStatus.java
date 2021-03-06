package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ServerStatus {
    CONNECTED("connected"),
    DISCONNECTED("disconnected");

    private final String value;

    ServerStatus(final String value) {
        this.value = value;
    }

    @JsonCreator
    public static ServerStatus forValue(String value) {
        return Arrays.stream(ServerStatus.values())
                .filter(status -> status.toValue().equals(value))
                .findFirst()
                .get();
    }

    @JsonValue
    public String toValue() {
        return value;
    }
}
