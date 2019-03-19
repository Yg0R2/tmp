package com.yg0r2.rms.ces.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.rms.ces.domain.ConfirmationEmailServiceRequest;
import com.yg0r2.rms.service.ResourcesUtils;

@Component
public class ConfirmationEmailServiceRequestFactory {

    private static final String CONFIRMATION_EMAIL_SERVICE_REQUEST_JSON = "classpath:CES_request.json";

    @Autowired
    private ResourcesUtils<ConfirmationEmailServiceRequest> cesResourceUtils;

    public ConfirmationEmailServiceRequest create() {
        return cesResourceUtils.read(CONFIRMATION_EMAIL_SERVICE_REQUEST_JSON);
    }

}
