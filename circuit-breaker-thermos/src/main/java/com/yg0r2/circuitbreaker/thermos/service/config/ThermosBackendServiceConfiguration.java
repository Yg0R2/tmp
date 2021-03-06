package com.yg0r2.circuitbreaker.thermos.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.thermos.ThermosHelper;

@Configuration
public class ThermosBackendServiceConfiguration {

    private static final String THERMOS_CONFIG_NAME = "thermosBackendService";

    @Value("${thermos.service-configs." + THERMOS_CONFIG_NAME + ".wrappedMethod.name}")
    private String wrappedMethodName;
    @Value("${thermos.service-configs." + THERMOS_CONFIG_NAME + ".wrappedMethod.argTypes}")
    private String wrappedMethodArgTypes;
    @Value("${thermos.service-configs." + THERMOS_CONFIG_NAME + ".commandName}")
    private String thermosCommandName;
    @Value("${thermos.service-configs." + THERMOS_CONFIG_NAME + ".groupName}")
    private String thermosGroupName;

    @Autowired
    private ThermosHelper<BackendService> thermosHelper;

    @Bean
    public CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker(BackendService thermosBackendService) {
        return thermosHelper.createCircuitBreaker(thermosBackendService, wrappedMethodName, wrappedMethodArgTypes, thermosCommandName, thermosGroupName, THERMOS_CONFIG_NAME);
    }

}
