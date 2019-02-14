package com.yg0r2.circuitbreaker.thermos.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.thermos.ThermosHelper;
import com.yg0r2.circuitbreaker.thermos.service.ThermosBackendService;

@Configuration
public class ThermosBackendServiceConfiguration {

    @Autowired
    private ThermosHelper thermosHelper;

    @Bean
    public BackendService thermosBackendService(CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker) {
        return thermosHelper.createCircuitBreakerProxy(createThermosBackendService(), "getData", "thermosBackendService");
    }

    @Bean
    public CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker() {
        return thermosHelper.createCircuitBreaker(createThermosBackendService(), "getData", "thermosBackendService");
    }

    private BackendService createThermosBackendService() {
        return new ThermosBackendService();
    }

}
