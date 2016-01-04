package com.indicators.models;

import javax.validation.Valid;

public class ServerResponse {
    @Valid
    private final SubscriptionStatus subscriptionStatus;

    @Valid
    private final SecurityInfo securityInfo;

    @Valid
    private final Quote quote;

    @Valid
    private final Error error;

    @Valid
    private final ServerStatus serverStatus;

    public ServerResponse() {
        subscriptionStatus = null;
        securityInfo = null;
        quote = null;
        error = null;
        serverStatus = null;
    }

    public ServerResponse(final SubscriptionStatus subscriptionStatus, final SecurityInfo securityInfo,
                          final Quote quote, final Error error, ServerStatus serverStatus) {
        this.subscriptionStatus = subscriptionStatus;
        this.securityInfo = securityInfo;
        this.quote = quote;
        this.error = error;
        this.serverStatus = serverStatus;
    }

    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public SecurityInfo getSecurityInfo() {
        return securityInfo;
    }

    public Quote getQuote() {
        return quote;
    }

    public Error getError() {
        return error;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }
}
