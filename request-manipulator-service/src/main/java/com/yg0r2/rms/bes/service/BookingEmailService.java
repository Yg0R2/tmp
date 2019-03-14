package com.yg0r2.rms.bes.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yg0r2.rms.bes.domain.BookingEmailServiceRequest;
import com.yg0r2.rms.common.domain.RequestContext;

@Component
public class BookingEmailService {

    @Value("${bes.url}")
    private String serviceUrl;

    @Autowired
    private RestTemplate besRestTemplate;

    public ResponseEntity<String> sendRequest(BookingEmailServiceRequest bookingEmailServiceRequest) {
        return besRestTemplate.postForEntity(serviceUrl, createHttpEntity(bookingEmailServiceRequest), String.class);
    }

    private HttpEntity<String> createHttpEntity(BookingEmailServiceRequest bookingEmailServiceRequest) {
        return new HttpEntity(bookingEmailServiceRequest, createHttpHeaders(bookingEmailServiceRequest.getRequestId(), bookingEmailServiceRequest.getRequestContext()));
    }

    private HttpHeaders createHttpHeaders(UUID requestId, RequestContext requestContext) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Request-Context-Brand", requestContext.getBrand());
        httpHeaders.set("Request-Context-Client-Id", requestContext.getClientId());
        httpHeaders.set("Request-Context-Locale", requestContext.getLocale());
        httpHeaders.set("Request-Context-Point-Of-Sale", requestContext.getPointOfSale());
        httpHeaders.set("Request-Context-Request-Id", requestId.toString());

        return httpHeaders;
    }

}
