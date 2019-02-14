package com.yg0r2.circuitbreaker.thermos;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotels.thermos.CircuitBreaker;
import com.hotels.thermos.CircuitBreakerConfiguration;
import com.hotels.thermos.ThermosEngine;
import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.hotels.thermos.proxy.MethodDefinition;
import com.hotels.thermos.spring.config.ThermosSpringConfig;

@Component
public class ThermosHelper {

    @Autowired
    private ThermosEngine thermosEngine;
    @Autowired
    private ThermosSpringConfig thermosSpring;

    public <T> T createCircuitBreakerProxy(Object proxyObject, String methodName, String thermosConfigName) {
        MethodDefinition methodDefinition = getMethodDefinition(methodName, thermosConfigName);

        return CircuitBreakerProxy.createProxy(proxyObject, Collections.singletonList(methodDefinition), true, thermosEngine);
    }

    public <T> CircuitBreakerProxy<T> createCircuitBreaker(Object proxyObject, String methodName, String thermosConfigName) {
        MethodDefinition methodDefinition = getMethodDefinition(methodName, thermosConfigName);

        return CircuitBreakerProxy.createCircuitBreaker(proxyObject, Collections.singletonList(methodDefinition), true, thermosEngine);
    }

    private MethodDefinition getMethodDefinition(String methodName, String thermosConfigName) {
        String methodArgTypes = null;
        String commandName = methodName + "Validation";
        String groupName = "validation";
        String fallbackExceptionClassName = RuntimeException.class.getName();

        CircuitBreakerConfiguration circuitBreakerConfiguration = getCircuitBreakerConfig(thermosConfigName);

        return new MethodDefinition(methodName, methodArgTypes, commandName, groupName, fallbackExceptionClassName, circuitBreakerConfiguration);
    }

    private CircuitBreakerConfiguration getCircuitBreakerConfig(String thermosConfigName) {
        return thermosSpring.getMethodConfigs().getWrapperConfig(thermosConfigName).getCircuitBreakerConfiguration();
    }


}
