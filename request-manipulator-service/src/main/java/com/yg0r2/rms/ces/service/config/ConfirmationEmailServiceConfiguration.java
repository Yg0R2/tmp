package com.yg0r2.rms.ces.service.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotels.messaging.MessageFactory;
import com.hotels.messaging.core.ConfigurableMessageFactory;
import com.hotels.services.common.messaging.v20100812.Error;
import com.hotels.services.common.messaging.v20100812.MessageEnvelope;
import com.hotels.services.eventservice.confemail.content.ConfirmationEmailMessage;
import com.yg0r2.rms.ces.domain.ConfirmationEmailServiceRequest;
import com.yg0r2.rms.service.EmailServiceRequestFactory;
import com.yg0r2.rms.service.ResourcesUtils;

@Configuration
public class ConfirmationEmailServiceConfiguration {

    private static final String CONFIRMATION_EMAIL_SERVICE_REQUEST_JSON = "classpath:CES_request.json";

    @Value("${application.title}")
    private String applicationTitle;
    @Value("${ces.messageVersion}")
    private String messageVersion;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RestTemplate cesRestTemplate() {
        return new RestTemplate(List.of(new Jaxb2RootElementHttpMessageConverter()));
    }

    @Bean
    public EmailServiceRequestFactory<ConfirmationEmailServiceRequest> confirmationEmailServiceRequestFactory() {
        return new EmailServiceRequestFactory<>(cesResourceUtils(), CONFIRMATION_EMAIL_SERVICE_REQUEST_JSON);
    }

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

    private ResourcesUtils<ConfirmationEmailServiceRequest> cesResourceUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

}
