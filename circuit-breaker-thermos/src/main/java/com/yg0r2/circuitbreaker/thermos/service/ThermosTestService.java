package com.yg0r2.circuitbreaker.thermos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotels.thermos.spring.annotations.ThermosWrapped;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;

public class ThermosTestService implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThermosTestService.class);

    private final BackendService backendService;

    public ThermosTestService(BackendService backendService) {
        this.backendService = backendService;
    }

    @ThermosWrapped(configName = "thermosTestService")
    @Override
    public String failingMethod() {
        LOGGER.info("inside ThermosTestService.failingMethod");

        return backendService.getData();
    }

    @Override
    public String fallbackMethod() {
        LOGGER.info("inside ThermosTestService.fallbackMethod");

        return "Fallback";
    }

}
