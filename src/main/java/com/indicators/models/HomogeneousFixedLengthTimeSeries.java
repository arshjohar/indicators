package com.indicators.models;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;

public class HomogeneousFixedLengthTimeSeries {
    private final int NUMBER_OF_ADDITIONAL_NON_HOMOGENEOUS_QUOTES = 1;
    private final ChronoUnit timeUnit;
    private final int length;
    private final LinkedList<Quote> quotes;
    private final Instrument instrument;

    public HomogeneousFixedLengthTimeSeries(final int length, final Instrument instrument) {
        this(length, instrument, ChronoUnit.MINUTES);
    }

    public HomogeneousFixedLengthTimeSeries(final int length, final Instrument instrument, ChronoUnit timeUnit) {
        this.timeUnit = timeUnit;
        this.length = length;
        this.quotes = new LinkedList<>();
        this.instrument = instrument;
    }

    public synchronized LinkedList<Quote> addQuote(final Quote quote) {
        if (!instrument.equals(quote.getInstrument())) {
            throw new IllegalArgumentException("Only quotes of the same instrument can be added");
        }

        if (!quotes.isEmpty()) {
            final Quote lastQuote = quotes.getLast();
            final Instant nextMinuteStart = truncateToTimeUnitAndAddAUnit(lastQuote.getSentAt());
            if (quote.getSentAt().isBefore(nextMinuteStart)) {
                quotes.removeLast();
            }
        }

        quotes.addLast(quote);

        if (quotes.size() > quotesRequiredToCalculateTrends()) {
            quotes.removeFirst();
        }

        return quotes;
    }

    public synchronized LinkedList<Quote> getQuotes() {
        return quotes;
    }

    public int getLength() {
        return length;
    }

    public boolean isFilled() {
        return quotesRequiredToCalculateTrends() == quotes.size();
    }

    private Instant truncateToTimeUnitAndAddAUnit(Instant instant) {
        return instant.truncatedTo(timeUnit).plus(1, timeUnit);
    }

    public Instant getLastTimestamp() {
        return getLastHomogeneousQuote().getSentAt();
    }

    public Quote getLastHomogeneousQuote() {
        return quotes.get(quotes.size() - 1);
    }

    private int quotesRequiredToCalculateTrends() {
        return length + NUMBER_OF_ADDITIONAL_NON_HOMOGENEOUS_QUOTES;
    }
}
