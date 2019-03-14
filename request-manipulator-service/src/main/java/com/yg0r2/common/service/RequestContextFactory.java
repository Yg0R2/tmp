package com.yg0r2.common.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.yg0r2.common.domain.RequestContext;

@Component
public class RequestContextFactory {

    public RequestContext create() {
        return new RequestContext.Builder()
            .withBrand("hotels.com")
            .withClientId("Spam-client")
            .withLocale("en_US")
            .withPointOfSale("HCOM_US")
            .withRequestId(UUID.randomUUID())
            .build();
    }
}
