package com.yg0r2.bes.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BookingEmailServiceConfiguration {

    @Bean
    public RestTemplate besRestTemplate() {
        return new RestTemplate();
    }

}
