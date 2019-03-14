package com.yg0r2.bes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yg0r2.bes.domain.BookingEmailRequest;
import com.yg0r2.common.domain.RequestContext;

@Component
public class BookingEmailService {

    @Value("${bes.url}")
    private String serviceUrl;

    @Autowired
    private RestTemplate besRestTemplate;

    public ResponseEntity<String> sendRequest(BookingEmailRequest bookingEmailRequest, RequestContext requestContext) {
        return besRestTemplate.postForEntity(serviceUrl, createHttpEntity(bookingEmailRequest, requestContext), String.class);
    }

    private HttpEntity<String> createHttpEntity(BookingEmailRequest bookingEmailRequest, RequestContext requestContext) {
        return new HttpEntity(bookingEmailRequest, createHttpHeaders(requestContext));
    }

    private HttpHeaders createHttpHeaders(RequestContext requestContext) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Request-Context-Brand", requestContext.getBrand());
        httpHeaders.set("Request-Context-Client-Id", requestContext.getClientId());
        httpHeaders.set("Request-Context-Locale", requestContext.getLocale());
        httpHeaders.set("Request-Context-Point-Of-Sale", requestContext.getPointOfSale());
        httpHeaders.set("Request-Context-Request-Id", requestContext.getRequestId().toString());

        return httpHeaders;
    }

}
