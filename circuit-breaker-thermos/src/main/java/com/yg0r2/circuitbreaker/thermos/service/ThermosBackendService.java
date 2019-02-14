package com.yg0r2.circuitbreaker.thermos.service;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hotels.thermos.spring.annotations.ThermosWrapped;
import com.yg0r2.circuitbreaker.api.BackendService;

@Component
public class ThermosBackendService implements BackendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThermosBackendService.class);
    private static final Random RND = new Random();

    private static int COUNTER = 0;

    @ThermosWrapped(configName = "thermosBackendService")
    @Override
    public String getData() {
        LOGGER.info("inside ThermosBackendService.getData: {}", ++COUNTER);

        if (RND.nextBoolean()) {
            throw new RuntimeException("FAILED");
        }

        return "SUCCESS";
    }

}
