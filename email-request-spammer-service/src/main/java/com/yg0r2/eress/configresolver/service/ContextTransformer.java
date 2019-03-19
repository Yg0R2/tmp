package com.yg0r2.eress.configresolver.service;

import org.springframework.stereotype.Component;

import com.hotels.platform.config.domain.Context;
import com.yg0r2.eress.domain.RequestContext;

@Component
public class ContextTransformer {

    public Context transform(RequestContext requestContext) {
        return new Context.Builder()
            .put("brand", requestContext.getBrand())
            .put("locale", requestContext.getLocale())
            .put("pos", requestContext.getPointOfSale())
            .build();
    }

}
