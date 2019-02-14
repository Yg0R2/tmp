package com.yg0r2.circuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService thermosTestService;
    @Autowired
    private CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker;

    @GetMapping("/test")
    public String testThermos() {
        LOGGER.info("isOpen: {}", thermosBackendServiceCircuitBreaker.isCircuitOpen("failingMethodValidation"));

        return thermosTestService.failingMethod();
    }

}
