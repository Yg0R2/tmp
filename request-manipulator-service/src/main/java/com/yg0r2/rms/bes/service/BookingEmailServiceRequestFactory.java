package com.yg0r2.rms.bes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.rms.bes.domain.BookingEmailServiceRequest;
import com.yg0r2.rms.common.service.ResourcesUtils;

@Component
public class BookingEmailServiceRequestFactory {

    private static final String BOOKING_EMAIL_SERVICE_REQUEST_JSON = "classpath:BES_request.json";

    @Autowired
    private ResourcesUtils<BookingEmailServiceRequest> besResourcesUtils;

    public BookingEmailServiceRequest create() {
        return besResourcesUtils.read(BOOKING_EMAIL_SERVICE_REQUEST_JSON);
    }

}
