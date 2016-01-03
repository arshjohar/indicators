package com.indicators.services;

import com.indicators.decoders.InstrumentSubscriptionRequestDecoder;
import com.indicators.encoders.JacksonEncoder;
import com.indicators.models.InstrumentSubscriptionRequest;
import com.indicators.models.ServerStatus;
import org.atmosphere.config.service.*;
import org.atmosphere.cpr.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@ManagedService(path = "/macd/{marketCode: [a-zA-Z0-9]*}/{securityCode: [a-zA-Z0-9]*}")
public class MACDIndicator {
    private final static String MACD = "/macd/";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PathParam("marketCode")
    private String marketCode;
    @PathParam("securityCode")
    private String securityCode;

    @Inject
    private BroadcasterFactory broadcasterFactory;

    @Inject
    private AtmosphereResourceFactory resourceFactory;

    @Inject
    private MetaBroadcaster metaBroadcaster;

    private static Set<String> getInstruments(Collection<Broadcaster> broadcasters) {
        Set<String> result = new HashSet<>();
        broadcasters.stream().filter(broadcaster -> !("/*".equals(broadcaster.getID()))).forEach(broadcaster -> {
            String[] p = broadcaster.getID().split("/");
            result.add(p.length > 2 ? p[2] : "");
        });
        return result;
    }

    @Ready(encoders = {JacksonEncoder.class})
    public ServerStatus onReady(final AtmosphereResource resource) {
        logger.info("Client {} connected.", resource.uuid());
        return ServerStatus.CONNECTED;
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

    @Message(encoders = {JacksonEncoder.class}, decoders = {InstrumentSubscriptionRequestDecoder.class})
    @DeliverTo(DeliverTo.DELIVER_TO.RESOURCE)
    public InstrumentSubscriptionRequest onMessage(final InstrumentSubscriptionRequest instrumentSubscriptionRequest)
            throws IOException {

        if (instrumentSubscriptionRequest.getSubscribe()) {
            users.remove(instrumentSubscriptionRequest.getAuthor());
            return new ChatProtocol(instrumentSubscriptionRequest.getAuthor(), " disconnected from room " + securityCode, users.keySet(), getInstruments(broadcasterFactory.lookupAll()));
        }

        if (!users.containsKey(instrumentSubscriptionRequest.getAuthor())) {
            users.put(instrumentSubscriptionRequest.getAuthor(), instrumentSubscriptionRequest.getUuid());
            return new ChatProtocol(instrumentSubscriptionRequest.getAuthor(), " entered room " + securityCode, users.keySet(), getInstruments(broadcasterFactory.lookupAll()));
        }

        instrumentSubscriptionRequest.setUsers(users.keySet());
        logger.info("{} just send {}", instrumentSubscriptionRequest.getAuthor(), instrumentSubscriptionRequest.getMessage());
        return new ChatProtocol(instrumentSubscriptionRequest.getAuthor(), instrumentSubscriptionRequest.getMessage(), users.keySet(), getInstruments(broadcasterFactory.lookupAll()));
    }

}
