package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.NotNull;

public class ServerStatus {
    private final Error error;
    @NotNull
    private final Status serverStatus;

    public ServerStatus(Error error, Status serverStatus) {
        this.error = error;
        this.serverStatus = serverStatus;
    }

    public enum Status {
        @JsonProperty("connected")
        CONNECTED,
        @JsonProperty("disconnected")
        DISCONNECTED
    }
}
