package com.indicators.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class TradingHours {
    private static final int MINUTES_IN_A_DAY = 24 * 60;

    @NotEmpty
    private final Set<Day> days;

    @Range(min = 0, max = MINUTES_IN_A_DAY)
    private final int closeMinute;

    @Min(value = 1)
    private final int length;

    public TradingHours(final Set<Day> days, final int closeMinute, final int length) {
        if (days == null) {
            this.days = Collections.emptySet();
        } else {
            this.days = Collections.unmodifiableSet(days);
        }
        this.closeMinute = closeMinute;
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TradingHours that = (TradingHours) o;

        if (closeMinute != that.closeMinute) {
            return false;
        }
        if (length != that.length) {
            return false;
        }
        return days.equals(that.days);

    }

    @Override
    public int hashCode() {
        return Objects.hash(days, closeMinute, length);
    }

    public Set<Day> getDays() {
        return days;
    }

    public int getCloseMinute() {
        return closeMinute;
    }

    public int getLength() {
        return length;
    }
}
