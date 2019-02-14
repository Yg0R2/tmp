package com.yg0r2.circuitbreaker.thermos;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;

import com.hotels.thermos.proxy.GenericProxyCircuitBreaker;

public class ThermosProxyCircuitBreaker<T> extends GenericProxyCircuitBreaker<T> {

    public ThermosProxyCircuitBreaker(T objectToProxy, Method method, Object[] methodArgs, String commandName, String groupName) {
        super(objectToProxy, method, methodArgs, commandName, groupName);
    }

    @Override
    public T executeFallback() {
        return (T) "fallbackMethod";
    }
}
