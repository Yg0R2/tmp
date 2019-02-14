package com.yg0r2.circuitbreaker.thermos.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.thermos.ThermosHelper;

@Configuration
public class ThermosBackendServiceConfiguration {

    private static final String METHOD_NAME = "getData";
    private static final String THERMOS_CONFIG_NAME = "thermosBackendService";

    @Autowired
    private ThermosHelper thermosHelper;

    @Bean
    public CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker(BackendService thermosBackendService) {
        return thermosHelper.createCircuitBreaker(thermosBackendService, METHOD_NAME, THERMOS_CONFIG_NAME);
    }

}
