package com.indicators.models;

public interface TrendCalculator {
    Trend calculate();

    HomogeneousFixedLengthTimeSeries getTimeSeries();
}
