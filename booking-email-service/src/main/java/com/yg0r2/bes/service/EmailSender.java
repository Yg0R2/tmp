package com.yg0r2.bes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.yg0r2.bes.domain.EmailRequest;
import com.yg0r2.bes.domain.RequestContext;

@Component
public class EmailSender {

    @Value("${emailSender.host}")
    private String serviceHost;
    @Value("${emailSender.endpoint}")
    private String serviceEndpoint;

    @Autowired
    private RestTemplate emailSenderRestTemplate;

    public ResponseEntity<String> sendEmail(EmailRequest emailRequest, RequestContext requestContext) {
        return emailSenderRestTemplate.postForEntity(serviceHost + "/" + serviceEndpoint, createHttpEntity(emailRequest, requestContext), String.class);
    }

    private HttpEntity<String> createHttpEntity(EmailRequest emailRequest, RequestContext requestContext) {
        return new HttpEntity(emailRequest, createHttpHeaders(requestContext));
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
