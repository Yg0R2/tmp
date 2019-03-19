package com.yg0r2.rms.bes.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.rms.bes.domain.BookingEmailServiceRequest;
import com.yg0r2.rms.service.EmailServiceRequestFactory;
import com.yg0r2.rms.service.ResourcesUtils;

@Configuration
public class BookingEmailServiceConfiguration {

    private static final String BOOKING_EMAIL_SERVICE_REQUEST_JSON = "classpath:BES_request.json";

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RestTemplate besRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public EmailServiceRequestFactory<BookingEmailServiceRequest> bookingEmailServiceRequestFactory() {
        return new EmailServiceRequestFactory<>(besResourcesUtils(), BOOKING_EMAIL_SERVICE_REQUEST_JSON);
    }

    public ResourcesUtils<BookingEmailServiceRequest> besResourcesUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

}
