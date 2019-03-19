package com.yg0r2.rms.bes.service;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.yg0r2.rms.bes.domain.BookingEmailServiceRequest;
import com.yg0r2.rms.domain.EmailResponse;
import com.yg0r2.rms.domain.RequestContext;
import com.yg0r2.rms.service.EmailService;

public class BookingEmailService extends EmailService<BookingEmailServiceRequest, EmailResponse> {

    public BookingEmailService(String serviceUrl, RestTemplate restTemplate) {
        super(serviceUrl, restTemplate, EmailResponse.class);
    }

    @Override
    public EmailResponse sendRequest(BookingEmailServiceRequest emailRequest) {
        return postForEntity(emailRequest).getBody();
    }

    @Override
    protected HttpHeaders createHttpHeaders(UUID requestId, RequestContext requestContext) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Request-Context-Brand", requestContext.getBrand());
        httpHeaders.set("Request-Context-Client-Id", requestContext.getClientId());
        httpHeaders.set("Request-Context-Locale", requestContext.getLocale());
        httpHeaders.set("Request-Context-Point-Of-Sale", requestContext.getPointOfSale());
        httpHeaders.set("Request-Context-Request-Id", requestId.toString());

        return httpHeaders;
    }

}
