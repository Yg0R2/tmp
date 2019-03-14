package com.yg0r2.ces.service.config;

import java.util.List;
import java.util.Map;

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
import com.yg0r2.common.service.ResourcesUtils;

@Configuration
public class ConfirmationEmailServiceConfiguration {

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
    public ResourcesUtils<Map<String, String>> cesResourceUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
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

}
