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
    private CircuitBreakerProxy<TestService> thermosTest1ServiceCircuitBreaker;
    @Autowired
    private CircuitBreakerProxy<TestService> thermosTest2ServiceCircuitBreaker;
    @Autowired
    private CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker;

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeExceptions() {
        HystrixCircuitBreaker hystrixTest1ServiceCircuitBreaker = getCircuitBreaker(testServiceThermosCommandName + 1);
        HystrixCircuitBreaker hystrixTest2ServiceCircuitBreaker = getCircuitBreaker(testServiceThermosCommandName + 2);
        HystrixCircuitBreaker hystrixBackendServiceCircuitBreaker = getCircuitBreaker(backendServiceThermosCommandName);

        StringBuilder sb = new StringBuilder();

        sb.append("<ul>");

        sb.append(createLi("Thermos Test1ServiceCircuitBreaker isOpen:", circuitBreakerIsOpen(thermosTest1ServiceCircuitBreaker, (testServiceThermosCommandName + 1))));
        sb.append(createLi("Thermos Test2ServiceCircuitBreaker isOpen:", circuitBreakerIsOpen(thermosTest2ServiceCircuitBreaker, (testServiceThermosCommandName + 2))));
        sb.append(createLi("Thermos BackendServiceCircuitBreaker isOpen:", circuitBreakerIsOpen(thermosBackendServiceCircuitBreaker, backendServiceThermosCommandName)));

        sb.append("<li></li>");

        sb.append(createLi("Hystrix Test1ServiceCircuitBreaker allowRequest:", circuitBreakerAllowsRequest(hystrixTest1ServiceCircuitBreaker)));
        sb.append(createLi("Hystrix Test2ServiceCircuitBreaker allowRequest:", circuitBreakerAllowsRequest(hystrixTest2ServiceCircuitBreaker)));
        sb.append(createLi("Hystrix BackendServiceCircuitBreaker allowRequest:", circuitBreakerAllowsRequest(hystrixBackendServiceCircuitBreaker)));

        sb.append("</ul>");

        return new ResponseEntity<>(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private StringBuilder createLi(String text, boolean value) {
        StringBuilder sb = new StringBuilder();

        sb.append("<li>");
        sb.append(text);
        sb.append(value);
        sb.append("</li>");

        return sb;
    }

    private HystrixCircuitBreaker getCircuitBreaker(String commandName) {
        return Optional.ofNullable(commandName)
            .map(HystrixCommandKey.Factory::asKey)
            .map(HystrixCircuitBreaker.Factory::getInstance)
            .orElse(null);
    }

    private boolean circuitBreakerIsOpen(CircuitBreakerProxy<?> circuitBreakerProxy, String commandName) {
        return circuitBreakerProxy.isCircuitOpen(commandName);
    }

    private boolean circuitBreakerAllowsRequest(HystrixCircuitBreaker hystrixCircuitBreaker) {
        return Optional.ofNullable(hystrixCircuitBreaker)
            .map(HystrixCircuitBreaker::allowRequest)
            .orElse(false);
    }

}
