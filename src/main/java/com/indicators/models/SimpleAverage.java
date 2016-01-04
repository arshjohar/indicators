package com.indicators.models;

import java.math.BigDecimal;
import java.util.List;

public class SimpleAverage implements TrendCalculator {
    private final int length;
    private final int timeSeriesLength;
    private final int numberOfQuotesToSkip;
    private final HomogeneousFixedLengthTimeSeries homogeneousFixedLengthTimeSeries;

    public SimpleAverage(final int length, final HomogeneousFixedLengthTimeSeries homogeneousFixedLengthTimeSeries) {
        this.timeSeriesLength = homogeneousFixedLengthTimeSeries.getLength();
        if (length > this.timeSeriesLength) {
            throw new IllegalArgumentException("Length can't be greater than " + this.timeSeriesLength);
        }
        this.length = length;
        this.homogeneousFixedLengthTimeSeries = homogeneousFixedLengthTimeSeries;
        this.numberOfQuotesToSkip = this.timeSeriesLength - this.length;
    }

    @Override
    public Trend calculate() {
        List<Quote> quoteList = homogeneousFixedLengthTimeSeries.getQuotes();
        final int numberOfQuotesNeeded = quoteList.size() - timeSeriesLength;
        if (numberOfQuotesNeeded > 0) {
            throw new IllegalStateException("Does not have enough quotes");
        }
        final BigDecimal sumOfQuotePrices = quoteList.stream()
                .skip(numberOfQuotesToSkip)
                .map(Quote::getLastPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        final BigDecimal average = sumOfQuotePrices.divide(BigDecimal.valueOf(this.length), BigDecimal.ROUND_HALF_UP);
        return new Trend(average, homogeneousFixedLengthTimeSeries.getLastTimestamp());
    }

    @Override
    public HomogeneousFixedLengthTimeSeries getTimeSeries() {
        return homogeneousFixedLengthTimeSeries;
    }
}
