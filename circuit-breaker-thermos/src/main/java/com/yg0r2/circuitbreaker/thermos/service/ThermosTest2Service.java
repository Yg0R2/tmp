package com.yg0r2.circuitbreaker.thermos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;

public class ThermosTest2Service implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThermosTest2Service.class);

    private static int COUNTER = 0;

    private final BackendService backendService;

    public ThermosTest2Service(BackendService backendService) {
        this.backendService = backendService;
    }

    @Override
    public String processRequest() {
        LOGGER.info("inside ThermosTest2Service.processRequest: {}", ++COUNTER);

        return backendService.getData();
    }

}
