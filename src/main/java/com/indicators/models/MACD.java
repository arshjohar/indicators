package com.indicators.models;

import java.math.BigDecimal;

public class MACD implements TrendCalculator {
    private final ExponentialAverage smallerEA;
    private final ExponentialAverage largerEA;
    private final HomogeneousFixedLengthTimeSeries homogeneousFixedLengthTimeSeries;

    public MACD(final Instrument instrument) {
        this(12, 26, instrument);
    }

    public MACD(final int smallerLength, final int largerLength, final Instrument instrument) {
        this.homogeneousFixedLengthTimeSeries = new HomogeneousFixedLengthTimeSeries(largerLength, instrument);
        this.smallerEA = new ExponentialAverage(smallerLength, homogeneousFixedLengthTimeSeries);
        this.largerEA = new ExponentialAverage(largerLength, homogeneousFixedLengthTimeSeries);
    }

    @Override
    public Trend calculate() {
        final BigDecimal smallerEAValue = smallerEA.calculate().getValue();
        final BigDecimal largerEAValue = largerEA.calculate().getValue();
        final BigDecimal value = smallerEAValue.subtract(largerEAValue);

        return new Trend(value, homogeneousFixedLengthTimeSeries.getLastTimestamp());
    }

    @Override
    public HomogeneousFixedLengthTimeSeries getTimeSeries() {
        return homogeneousFixedLengthTimeSeries;
    }
}
