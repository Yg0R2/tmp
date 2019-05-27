package com.yg0r2.eress.ces.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotels.messaging.MessageFactory;
import com.hotels.messaging.core.ConfigurableMessageFactory;
import com.hotels.services.common.messaging.v20100812.Error;
import com.hotels.services.common.messaging.v20100812.MessageEnvelope;
import com.hotels.services.eventservice.confemail.content.ConfirmationEmailMessage;
import com.hotels.services.eventservice.confemail.response.EventServiceConfirmationEmailResponse;
import com.yg0r2.eress.ces.domain.ConfirmationEmailServiceRequest;
import com.yg0r2.eress.ces.service.ConfirmationEmailMessageTransformer;
import com.yg0r2.eress.ces.service.ConfirmationEmailService;
import com.yg0r2.eress.service.EmailService;
import com.yg0r2.eress.service.EmailServiceCallable;
import com.yg0r2.eress.service.EmailServiceRequestFactory;
import com.yg0r2.eress.service.EmailServiceSpammer;
import com.yg0r2.eress.service.ResourcesUtils;

@Configuration
public class ConfirmationEmailServiceConfiguration {

    private static final String CONFIRMATION_EMAIL_SERVICE_REQUEST_JSON = "classpath:CES_request.json";

    @Value("${application.title}")
    private String applicationTitle;
    @Value("${ces.messageVersion}")
    private String messageVersion;
    @Value("${ces.url}")
    private String serviceUrl;

    @Autowired
    private ClientHttpRequestFactory clientHttpRequestFactory;
    @Autowired
    private ConfirmationEmailMessageTransformer confirmationEmailMessageTransformer;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public MessageFactory<ConfirmationEmailMessage, Error> messageFactory() {
        ConfigurableMessageFactory<ConfirmationEmailMessage, MessageEnvelope, Error> configurableMessageFactory = new ConfigurableMessageFactory<>();

        configurableMessageFactory.setSenderName(applicationTitle);
        configurableMessageFactory.setEnvelopeClass(MessageEnvelope.class);
        configurableMessageFactory.setErrorClass(Error.class);
        configurableMessageFactory.setMessageClass(ConfirmationEmailMessage.class);
        configurableMessageFactory.setVersion(messageVersion);

        return configurableMessageFactory;
    }

    @Bean
    public EmailServiceSpammer cesSpammer() {
        return new EmailServiceSpammer(cesCallable());
    }

    private EmailServiceCallable<ConfirmationEmailServiceRequest, EventServiceConfirmationEmailResponse> cesCallable() {
        return new EmailServiceCallable<>(cesRequestFactory(), cesEmailService());
    }

    private EmailServiceRequestFactory<ConfirmationEmailServiceRequest> cesRequestFactory() {
        return new EmailServiceRequestFactory<>(cesResourceUtils(), CONFIRMATION_EMAIL_SERVICE_REQUEST_JSON);
    }

    private ResourcesUtils<ConfirmationEmailServiceRequest> cesResourceUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

    private EmailService<ConfirmationEmailServiceRequest, EventServiceConfirmationEmailResponse> cesEmailService() {
        return new ConfirmationEmailService(serviceUrl, cesRestTemplate(), confirmationEmailMessageTransformer);
    }

    private RestTemplate cesRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(List.of(new Jaxb2RootElementHttpMessageConverter()));

        restTemplate.setRequestFactory(clientHttpRequestFactory);

        return restTemplate;
    }

}
