package com.yg0r2.circuitbreaker.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yg0r2.circuitbreaker.api.BackendService;

@Service
public class HystrixBackendService implements BackendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixBackendService.class);

    @HystrixCommand(groupKey = "backend", fallbackMethod = "fallbackThrowException", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "2"),
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
        @HystrixProperty(name = "requestCache.enabled", value = "false")
    })
    @Override
    public String getData() {
        LOGGER.info("inside HystrixBackendService.getData");
return "alma";
        //throw new RuntimeException("FAILED");
    }

    private String throwException() {
        LOGGER.info("inside HystrixBackendService.fallbackThrowException");

        throw new RuntimeException("fallback");
    }
}
