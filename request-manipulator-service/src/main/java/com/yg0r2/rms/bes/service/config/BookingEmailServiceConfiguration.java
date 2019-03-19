package com.yg0r2.rms.bes.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.rms.bes.domain.BookingEmailServiceRequest;
import com.yg0r2.rms.service.ResourcesUtils;

@Configuration
public class BookingEmailServiceConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RestTemplate besRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ResourcesUtils<BookingEmailServiceRequest> besResourcesUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

}
