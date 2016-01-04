package com.indicators.models;

import javax.validation.constraints.NotNull;

public class Error {
    private final int id;
    @NotNull
    private final String message;

    public Error(final int id, final String message) {
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
