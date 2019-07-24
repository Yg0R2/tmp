package com.yg0r2.tmp.resttemplate.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.yg0r2.tmp.resttemplate.rest.DefaultErrorHandler;

@Configuration
public class RestTemplateConfiguration {

    @Autowired
//    private ResponseErrorHandler responseErrorHandler;
    private DefaultErrorHandler responseErrorHandler;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        return restTemplateBuilder
            .errorHandler(responseErrorHandler)
            //.setConnectTimeout(Duration.ofMillis(1000))
            //.setReadTimeout(Duration.ofMillis(1000))
            .build();
    }

}
