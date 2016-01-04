package com.indicators.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indicators.models.*;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.atmosphere.config.service.*;
import org.atmosphere.cpr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Singleton
@ManagedService(path = "/macd")
public class MACDIndicator {
    private final static String MACD_PATH = "/macd";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebSocket webSocketClient;
    @Inject
    private BroadcasterFactory broadcasterFactory;
    @Inject
    private AtmosphereResourceFactory resourceFactory;
    @Inject
    private MetaBroadcaster metaBroadcaster;
    @Inject
    private ObjectMapper mapper;
    private Map<Instrument, TrendCalculator> trendsMap = new HashMap<>();
    private CircularFifoQueue<Trend> trendCache = new CircularFifoQueue<>(50);

    @Ready
    public void onReady(final AtmosphereResource resource) throws ExecutionException, InterruptedException {
        logger.info("Client {} connected.", resource.uuid());
    }

    @Disconnect
    public void onDisconnect(final AtmosphereResourceEvent atmosphereResourceEvent) {
        String uuid = atmosphereResourceEvent.getResource().uuid();
        if (atmosphereResourceEvent.isCancelled()) {
            logger.info("Client {} unexpectedly disconnected", uuid);
        } else if (atmosphereResourceEvent.isClosedByClient()) {
            logger.info("Client {} closed the connection", uuid);
        }
    }

    @Message
    public void onMessage(final AtmosphereResource resource, final String instrumentSubscriptionRequest) throws ExecutionException, InterruptedException {
        if (webSocketClient == null) {
            AsyncHttpClientConfig clientConfig = new AsyncHttpClientConfig.Builder().setAcceptAnyCertificate(true).build();
            webSocketClient = new AsyncHttpClient(clientConfig).prepareGet("wss://a.stealthtrader.com:7339/")
                    .execute(new WebSocketUpgradeHandler.Builder().build()).get();
            webSocketClient.addWebSocketListener(new WebSocketTextListener() {
                @Override
                public void onMessage(String response) {
                    final ServerResponse serverResponse;
                    final Broadcaster broadcaster = broadcasterFactory.lookup(MACD_PATH);
                    try {
                        serverResponse = mapper.readValue(response, ServerResponse.class);
                        final Quote quote = serverResponse.getQuote();
                        final ServerStatus serverStatus = serverResponse.getServerStatus();
                        final SecurityInfo securityInfo = serverResponse.getSecurityInfo();
                        if (quote != null) {
                            final Instrument instrument = quote.getInstrument();
                            final TrendCalculator trend = trendsMap.get(instrument);
                            final HomogeneousFixedLengthTimeSeries timeSeries = trend.getTimeSeries();
                            synchronized (timeSeries) {
                                timeSeries.addQuote(quote);
                                if (timeSeries.isFilled()) {
                                    trendCache.add(trend.calculate());
                                }
                                broadcaster.broadcast(mapper.writeValueAsString(new IndicatorServerResponse(trendCache)));
                            }
                        } else if (securityInfo != null) {
                            final Instrument instrument = securityInfo.getInstrument();
                            trendsMap.put(instrument, new MACD(instrument));
                        } else if (serverResponse.getError() != null) {
                            resource.write(response);
                        } else if (serverStatus != null) {
                            if (serverStatus == ServerStatus.DISCONNECTED) {
                                try {
                                    resource.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            resource.write(response);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onOpen(WebSocket webSocket) {

                }

                @Override
                public void onClose(WebSocket webSocket) {

                }

                @Override
                public void onError(Throwable throwable) {
                }
            });
        }
        webSocketClient.sendMessage(instrumentSubscriptionRequest);
    }
}
