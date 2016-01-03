package com.indicators.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.List;

public class TradingHours {
    @NotNull
    private final List<DayOfWeek> days;
    @Range(min = 0, max = 1440)
    private final int closeMinute;
    private final int length;

    @JsonCreator
    public TradingHours(@JsonProperty("days") final List<DayOfWeek> days,
                        @JsonProperty("closeMinute") final int closeMinute,
                        @JsonProperty("length") final int length) {
        this.days = days;
        this.closeMinute = closeMinute;
        this.length = length;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public int getCloseMinute() {
        return closeMinute;
    }

    public int getLength() {
        return length;
    }
}
