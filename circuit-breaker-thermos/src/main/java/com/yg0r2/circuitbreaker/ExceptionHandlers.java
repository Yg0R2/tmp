package com.yg0r2.circuitbreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hotels.thermos.proxy.CircuitBreakerProxy;
import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;

@RestControllerAdvice
public class ExceptionHandlers {

    @Autowired
    private CircuitBreakerProxy<TestService> thermosTestServiceCircuitBreaker;
    @Autowired
    private CircuitBreakerProxy<BackendService> thermosBackendServiceCircuitBreaker;

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> handleRuntimeExceptions() {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul><li>");
        sb.append("thermosTestServiceCircuitBreaker isOpen:");
        sb.append(thermosTestServiceCircuitBreaker.isCircuitOpen("failingMethodValidation"));
        sb.append("</li><li>");
        sb.append("thermosBackendServiceCircuitBreaker isOpen:");
        sb.append(thermosBackendServiceCircuitBreaker.isCircuitOpen("getDataValidation"));
        sb.append("</li></ul>");

        return new ResponseEntity<>(sb.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
