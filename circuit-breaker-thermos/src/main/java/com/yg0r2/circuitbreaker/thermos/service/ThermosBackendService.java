package com.yg0r2.circuitbreaker.thermos.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotels.thermos.spring.annotations.ThermosWrapped;
import com.yg0r2.circuitbreaker.api.BackendService;

public class ThermosBackendService implements BackendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThermosBackendService.class);

    @ThermosWrapped(configName = "thermosBackendService")
    @Override
    public String getData() {
        LOGGER.info("inside ThermosBackendService.getData");

        throw new RuntimeException("FAILED");
    }

}
