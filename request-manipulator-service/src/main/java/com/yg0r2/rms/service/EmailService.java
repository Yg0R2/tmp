package com.yg0r2.rms.service;

import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.yg0r2.rms.domain.EmailRequest;
import com.yg0r2.rms.domain.EmailResponse;
import com.yg0r2.rms.domain.RequestContext;

public abstract class EmailService<REQUEST extends EmailRequest, RESPONSE> {

    private final String serviceUrl;
    private final RestTemplate restTemplate;
    private final Class<RESPONSE> responseType;

    public EmailService(String serviceUrl, RestTemplate restTemplate, Class<RESPONSE> responseType) {
        this.serviceUrl = serviceUrl;
        this.restTemplate = restTemplate;
        this.responseType = responseType;
    }

    public abstract EmailResponse sendRequest(REQUEST emailRequest);

    protected ResponseEntity<RESPONSE> postForEntity(REQUEST emailRequest) {
        return restTemplate.postForEntity(serviceUrl, createHttpEntity(emailRequest), responseType);
    }

    protected HttpEntity<String> createHttpEntity(REQUEST emailRequest) {
        return new HttpEntity(emailRequest, createHttpHeaders(emailRequest.getRequestId(), emailRequest.getRequestContext()));
    }

    protected abstract HttpHeaders createHttpHeaders(UUID requestId, RequestContext requestContext);

}
