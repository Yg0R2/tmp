package com.yg0r2.circuitbreaker.thermos.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;
import com.yg0r2.circuitbreaker.thermos.ThermosHelper;
import com.yg0r2.circuitbreaker.thermos.service.ThermosTest1Service;

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
    private ThermosHelper<TestService> thermosHelper;

    @Bean
    public TestService thermosTest1Service(TestService defaultThermosTestService) {
        return thermosHelper.createCircuitBreakerProxy(defaultThermosTestService, wrappedMethodName, wrappedMethodArgTypes, thermosCommandName + 1, thermosGroupName, THERMOS_CONFIG_NAME);
    }

    @Bean
    public CircuitBreakerProxy<TestService> thermosTest1ServiceCircuitBreaker(TestService thermosTest1Service) {
        return thermosHelper.createCircuitBreaker(thermosTest1Service, wrappedMethodName, wrappedMethodArgTypes, thermosCommandName + 1, thermosGroupName, THERMOS_CONFIG_NAME);
    }

    @Bean
    public TestService thermosTest2Service(TestService defaultThermosTestService) {
        return thermosHelper.createCircuitBreakerProxy(defaultThermosTestService, wrappedMethodName, wrappedMethodArgTypes, thermosCommandName + 2, thermosGroupName, THERMOS_CONFIG_NAME);
    }

    @Bean
    public CircuitBreakerProxy<TestService> thermosTest2ServiceCircuitBreaker(TestService thermosTest2Service) {
        return thermosHelper.createCircuitBreaker(thermosTest2Service, wrappedMethodName, wrappedMethodArgTypes, thermosCommandName + 2, thermosGroupName, THERMOS_CONFIG_NAME);
    }

    @Bean
    protected TestService defaultThermosTestService() {
        return new ThermosTest1Service(thermosBackendService);
    }

}
