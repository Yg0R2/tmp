package com.yg0r2.circuitbreaker;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.yg0r2.circuitbreaker.api.TestService;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static int COUNTER = 0;

    @Autowired
    private TestService thermosTestService;

    @GetMapping("/test")
    public String testThermos() {
        LOGGER.info("TestController call counter: {}", ++COUNTER);
        logHystrix();

        return thermosTestService.failingMethod();
    }

    private void logHystrix() {
        HystrixCircuitBreaker hystrixCircuitBreaker = getCircuitBreaker();
        boolean isOpen = circuitBreakerAllowsRequest(hystrixCircuitBreaker);

        LOGGER.info("hystrixCircuitBreaker: {}", hystrixCircuitBreaker);
        LOGGER.info("isOpen: {}", isOpen);
    }

    private boolean circuitBreakerAllowsRequest(HystrixCircuitBreaker hystrixCircuitBreaker) {
        return Optional.ofNullable(hystrixCircuitBreaker).map(cb -> !cb.allowRequest()).orElse(false);
    }

    private HystrixCircuitBreaker getCircuitBreaker() {
        HystrixCommandKey hystrixCommandKey = HystrixCommandKey.Factory.asKey("failingMethod");
        return HystrixCircuitBreaker.Factory.getInstance(hystrixCommandKey);
    }

}
