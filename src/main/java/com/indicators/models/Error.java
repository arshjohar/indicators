package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.sun.istack.internal.NotNull;

@JsonRootName(value = "error")
public class Error {
    @NotNull
    private final int id;
    @NotNull
    private final String message;

    @JsonCreator
    public Error(@JsonProperty("id") final int id, @JsonProperty("message") final String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
