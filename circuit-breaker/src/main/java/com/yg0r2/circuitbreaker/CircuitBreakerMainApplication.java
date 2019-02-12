package com.yg0r2.circuitbreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableCircuitBreaker
@SpringBootApplication
public class CircuitBreakerMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(CircuitBreakerMainApplication.class, args);
    }

}
