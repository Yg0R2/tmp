package com.yg0r2.rms.ces.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hotels.services.eventservice.confemail.content.ConfirmationEmailMessage;
import com.hotels.services.eventservice.confemail.response.EventServiceConfirmationEmailResponse;
import com.yg0r2.rms.ces.domain.ConfirmationEmailServiceRequest;
import com.yg0r2.rms.domain.EmailResponse;
import com.yg0r2.rms.domain.RequestContext;
import com.yg0r2.rms.service.EmailService;

public class ConfirmationEmailService extends EmailService<ConfirmationEmailServiceRequest, EventServiceConfirmationEmailResponse> {

    private final ConfirmationEmailMessageTransformer confirmationEmailMessageTransformer;

    public ConfirmationEmailService(String serviceUrl, RestTemplate cesRestTemplate, ConfirmationEmailMessageTransformer confirmationEmailMessageTransformer) {
        super(serviceUrl, cesRestTemplate, EventServiceConfirmationEmailResponse.class);

        this.confirmationEmailMessageTransformer = confirmationEmailMessageTransformer;
    }

    @Override
    public EmailResponse sendRequest(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        ResponseEntity<EventServiceConfirmationEmailResponse> response = postForEntity(confirmationEmailServiceRequest);

        return new EmailResponse.Builder()
            .withRequestId(response.getBody().getRequestMessageEnvelope().getId())
            .build();
    }

    @Override
    protected HttpEntity<String> createHttpEntity(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        ConfirmationEmailMessage confirmationEmailMessage = confirmationEmailMessageTransformer.transform(confirmationEmailServiceRequest);

        return new HttpEntity(confirmationEmailMessage, createHttpHeaders(confirmationEmailServiceRequest.getRequestId(), confirmationEmailServiceRequest.getRequestContext()));
    }

    @Override
    protected HttpHeaders createHttpHeaders(UUID requestId, RequestContext requestContext) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("requestId", requestId.toString());

        return httpHeaders;
    }

}
