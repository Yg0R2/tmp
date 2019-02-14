package com.yg0r2.circuitbreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class CircuitBreakerHystrixMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerHystrixMainApplication.class, args);
    }

}
