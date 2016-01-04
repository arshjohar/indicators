package com.indicators.models;

import java.util.Queue;

public class IndicatorServerResponse {
    private final Queue<Trend> trends;

    public IndicatorServerResponse(Queue<Trend> trends) {
        this.trends = trends;
    }
}
