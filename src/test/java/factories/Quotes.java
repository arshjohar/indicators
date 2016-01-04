package factories;

import com.indicators.models.Instrument;
import com.indicators.models.Quote;

import java.math.BigDecimal;
import java.time.Instant;

public class Quotes {
    public static Quote build() {
        final String marketCode = "XNYS";
        final String securityCode = "MSFT";
        final BigDecimal askPrice = new BigDecimal("47.67");
        final int askSize = 4600;
        final BigDecimal bidPrice = new BigDecimal("47.66");
        final int bidSize = 8700;
        final BigDecimal lastPrice = new BigDecimal("47.66");
        final int lastSize = 100;
        final Instant sentAt = Instant.parse("2015-10-19T19:22:11.7767845Z");
        final Instrument instrument = new Instrument(marketCode, securityCode);

        return new Quote(instrument.getMarketCode(), instrument.getSecurityCode(), bidPrice, bidSize, askPrice,
                askSize, lastPrice, lastSize, sentAt);
    }
}
