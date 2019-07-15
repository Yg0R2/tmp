package com.yg0r2.circuitbreaker.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;

@Service
public class HystrixTestService implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixTestService.class);

    @Autowired
    private BackendService hystrixBackendService;

    @HystrixCommand(groupKey = "service", fallbackMethod = "fallbackMethod", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "2"),
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
        @HystrixProperty(name = "requestCache.enabled", value = "false")
    })
    @Override
    public String failingMethod() {
        LOGGER.info("inside HystrixTestService.failingMethod");

        return hystrixBackendService.getData();
    }

    @Override
    public String fallbackMethod() {
        LOGGER.info("inside HystrixTestService.fallbackMethod");

        return "HystrixTestService.fallbackMethod";
    }

}
