package com.yg0r2.rms.ces.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotels.services.eventservice.confemail.content.ConfirmationEmailMessage;
import com.hotels.services.eventservice.confemail.response.EventServiceConfirmationEmailResponse;
import com.yg0r2.rms.ces.domain.ConfirmationEmailServiceRequest;

@Service
public class ConfirmationEmailService {

    @Value("${ces.url}")
    private String serviceUrl;

    @Autowired
    private ConfirmationEmailMessageTransformer confirmationEmailMessageTransformer;

    @Autowired
    private RestTemplate cesRestTemplate;

    public Map<String, Object> sendRequest(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        ResponseEntity<EventServiceConfirmationEmailResponse> response = postForEntity(confirmationEmailServiceRequest);

        return Map.ofEntries(
            Map.entry("requestId", confirmationEmailServiceRequest.getRequestId()),
            Map.entry("status", response.getStatusCode())
        );
    }

    private ResponseEntity<EventServiceConfirmationEmailResponse> postForEntity(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        HttpEntity<String> httpEntity = createHttpEntity(confirmationEmailServiceRequest);

        return cesRestTemplate.postForEntity(serviceUrl, httpEntity, EventServiceConfirmationEmailResponse.class);
    }

    private HttpEntity<String> createHttpEntity(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        ConfirmationEmailMessage confirmationEmailMessage = confirmationEmailMessageTransformer.transform(confirmationEmailServiceRequest);

        return new HttpEntity(confirmationEmailMessage, createHttpHeaders(confirmationEmailServiceRequest.getRequestId()));
    }

    private HttpHeaders createHttpHeaders(UUID requestId) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("requestId", requestId.toString());

        return httpHeaders;
    }

}
