package com.yg0r2.circuitbreaker.thermos.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;
import com.yg0r2.circuitbreaker.thermos.ThermosHelper;
import com.yg0r2.circuitbreaker.thermos.service.ThermosTestService;

@Configuration
public class ThermosTestServiceConfiguration {

    private static final String METHOD_NAME = "failingMethod";
    private static final String THERMOS_CONFIG_NAME = "thermosTestService";

    @Autowired
    private BackendService thermosBackendService;
    @Autowired
    private ThermosHelper thermosHelper;

    @Bean
    public TestService thermosTestService(TestService defaultThermosTestService) {
        return thermosHelper.createCircuitBreakerProxy(defaultThermosTestService, METHOD_NAME, THERMOS_CONFIG_NAME);
    }

    @Bean
    public CircuitBreakerProxy<TestService> thermosTestServiceCircuitBreaker(TestService thermosTestService) {
        return thermosHelper.createCircuitBreaker(thermosTestService, METHOD_NAME, THERMOS_CONFIG_NAME);
    }

    @Bean
    protected TestService defaultThermosTestService() {
        return new ThermosTestService(thermosBackendService);
    }

}
