package com.yg0r2.circuitbreaker.thermos.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;
import com.yg0r2.circuitbreaker.thermos.ThermosHelper;
import com.yg0r2.circuitbreaker.thermos.service.ThermosTestService;

@Configuration
public class ThermosTestServiceConfiguration {

    private static final String THERMOS_CONFIG_NAME = "thermosTestService";

    @Value("${thermos.method-configs." + THERMOS_CONFIG_NAME + ".wrappedMethod.name}")
    private String wrappedMethodName;
    @Value("${thermos.method-configs." + THERMOS_CONFIG_NAME + ".wrappedMethod.argTypes}")
    private String wrappedMethodArgTypes;
    @Value("${thermos.method-configs." + THERMOS_CONFIG_NAME + ".commandName}")
    private String thermosCommandName;
    @Value("${thermos.method-configs." + THERMOS_CONFIG_NAME + ".groupName}")
    private String thermosGroupName;

    @Autowired
    private BackendService thermosBackendService;
    @Autowired
    private ThermosHelper thermosHelper;

    @Bean
    public TestService thermosTestService(TestService defaultThermosTestService) {
        return thermosHelper.createCircuitBreakerProxy(defaultThermosTestService, wrappedMethodName, wrappedMethodArgTypes, thermosCommandName, thermosGroupName, THERMOS_CONFIG_NAME);
    }

    @Bean
    public CircuitBreakerProxy<TestService> thermosTestServiceCircuitBreaker(TestService thermosTestService) {
        return thermosHelper.createCircuitBreaker(thermosTestService, wrappedMethodName, wrappedMethodArgTypes, thermosCommandName, thermosGroupName, THERMOS_CONFIG_NAME);
    }

    @Bean
    protected TestService defaultThermosTestService() {
        return new ThermosTestService(thermosBackendService);
    }

}
