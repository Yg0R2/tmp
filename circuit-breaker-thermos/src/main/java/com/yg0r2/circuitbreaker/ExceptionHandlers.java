package com.yg0r2.circuitbreaker;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;

@RestControllerAdvice
public class ExceptionHandlers {

    @Value("${thermos.method-configs.thermosTestService.commandName}")
    private String testServiceThermosCommandName;
    @Value("${thermos.method-configs.thermosBackendService.commandName}")
    private String backendServiceThermosCommandName;

    @Autowired
    private CircuitBreakerProxy<TestService> thermosTestServiceCircuitBreaker;
    @Autowired
    private CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker;

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeExceptions() {
        HystrixCircuitBreaker testServiceHystrixCircuitBreaker = getCircuitBreaker(testServiceThermosCommandName);
        boolean testServiceHystrixCircuitBreakerAllowRequest = circuitBreakerAllowsRequest(testServiceHystrixCircuitBreaker);
        HystrixCircuitBreaker backendServiceHystrixCircuitBreaker = getCircuitBreaker(backendServiceThermosCommandName);
        boolean backendServiceHystrixCircuitBreakerAllowRequest = circuitBreakerAllowsRequest(backendServiceHystrixCircuitBreaker);

        StringBuilder sb = new StringBuilder();
        sb.append("<ul><li>");
        sb.append("thermos TestServiceCircuitBreaker isOpen:");
        sb.append(thermosTestServiceCircuitBreaker.isCircuitOpen(testServiceThermosCommandName));
        sb.append("</li><li>");
        sb.append("thermos BackendServiceCircuitBreaker isOpen:");
        sb.append(thermosBackendServiceCircuitBreaker.isCircuitOpen(backendServiceThermosCommandName));
        sb.append("</li><li>");
        sb.append("Hystrix TestService CircuitBreaker allowRequest:");
        sb.append(testServiceHystrixCircuitBreakerAllowRequest);
        sb.append("</li><li>");
        sb.append("Hystrix BackendService CircuitBreaker allowRequest:");
        sb.append(backendServiceHystrixCircuitBreakerAllowRequest);
        sb.append("</li></ul>");

        return new ResponseEntity<>(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HystrixCircuitBreaker getCircuitBreaker(String commandName) {
        HystrixCommandKey hystrixCommandKey = HystrixCommandKey.Factory.asKey(commandName);
        return HystrixCircuitBreaker.Factory.getInstance(hystrixCommandKey);
    }

    private boolean circuitBreakerAllowsRequest(HystrixCircuitBreaker hystrixCircuitBreaker) {
        return Optional.ofNullable(hystrixCircuitBreaker).map(HystrixCircuitBreaker::allowRequest).orElse(false);
    }

}
