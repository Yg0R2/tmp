package com.yg0r2.ces.service;

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
import com.yg0r2.ces.domain.ConfirmationEmailRequest;
import com.yg0r2.common.domain.RequestContext;

@Service
public class ConfirmationEmailService {

    @Value("${ces.url}")
    private String serviceUrl;

    @Autowired
    private ConfirmationEmailMessageTransformer confirmationEmailMessageTransformer;

    @Autowired
    private RestTemplate cesRestTemplate;

    public HttpStatus sendRequest(ConfirmationEmailRequest confirmationEmailRequest, RequestContext requestContext) {
        ResponseEntity<EventServiceConfirmationEmailResponse> response = postForEntity(confirmationEmailRequest, requestContext);

        return response.getStatusCode();
    }

    private ResponseEntity<EventServiceConfirmationEmailResponse> postForEntity(ConfirmationEmailRequest confirmationEmailRequest, RequestContext requestContext) {
        ConfirmationEmailMessage confirmationEmailMessage = confirmationEmailMessageTransformer.transform(confirmationEmailRequest);

        return cesRestTemplate.postForEntity(serviceUrl, createHttpEntity(confirmationEmailMessage, requestContext), EventServiceConfirmationEmailResponse.class);
    }

    private HttpEntity<String> createHttpEntity(ConfirmationEmailMessage confirmationEmailMessage, RequestContext requestContext) {
        return new HttpEntity(confirmationEmailMessage, createHttpHeaders(requestContext));
    }

    private HttpHeaders createHttpHeaders(RequestContext requestContext) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("requestId", requestContext.getRequestId().toString());

        return httpHeaders;
    }

}
