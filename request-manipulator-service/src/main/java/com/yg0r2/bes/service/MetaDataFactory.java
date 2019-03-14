package com.yg0r2.bes.service;

import org.springframework.stereotype.Component;

import com.yg0r2.bes.domain.MetaData;

@Component
public class MetaDataFactory {

    public MetaData create() {
        return new MetaData.Builder()
            .withEmailOrigin("T_TR_CONF_RESEND_CONF_PAGE")
            .build();
    }

}
