package com.indicators.models;

import java.math.BigDecimal;
import java.util.Deque;

public class ExponentialAverage implements TrendCalculator {
    private final HomogeneousFixedLengthTimeSeries homogeneousFixedLengthTimeSeries;
    private final SimpleAverage simpleAverage;
    private final BigDecimal smoothingConstant;
    private BigDecimal previousValue;

    public ExponentialAverage(final int length, final HomogeneousFixedLengthTimeSeries homogeneousFixedLengthTimeSeries) {
        this.homogeneousFixedLengthTimeSeries = homogeneousFixedLengthTimeSeries;
        this.simpleAverage = new SimpleAverage(length, homogeneousFixedLengthTimeSeries);
        this.smoothingConstant = new BigDecimal(2).divide(new BigDecimal(length + 1), 6, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public Trend calculate() {
        final BigDecimal value;
        if (previousValue == null) {
            value = simpleAverage.calculate().getValue();
        } else {
            Deque<Quote> quoteList = homogeneousFixedLengthTimeSeries.getQuotes();
            BigDecimal closePrice = quoteList.getLast().getLastPrice();
            value = smoothingConstant.multiply(closePrice.subtract(previousValue)).add(previousValue);
        }
        previousValue = value;

        return new Trend(value, homogeneousFixedLengthTimeSeries.getLastTimestamp());
    }

    @Override
    public HomogeneousFixedLengthTimeSeries getTimeSeries() {
        return homogeneousFixedLengthTimeSeries;
    }
}
