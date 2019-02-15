package com.yg0r2.circuitbreaker.thermos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;

public class ThermosTestService implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThermosTestService.class);

    private static int COUNTER = 0;

    private final BackendService backendService;

    public ThermosTestService(BackendService backendService) {
        this.backendService = backendService;
    }

    @Override
    public String processRequest() {
        LOGGER.info("inside ThermosTestService.processRequest: {}", ++COUNTER);

        return backendService.getData();
    }

}
