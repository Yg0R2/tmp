package com.yg0r2.tmp.resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class RestTemplateTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateTestApplication.class, args);
    }
}
