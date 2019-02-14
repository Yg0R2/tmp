package com.yg0r2.circuitbreaker.thermos.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yg0r2.circuitbreaker.api.BackendService;
import com.yg0r2.circuitbreaker.api.TestService;
import com.yg0r2.circuitbreaker.thermos.ThermosHelper;
import com.yg0r2.circuitbreaker.thermos.service.ThermosTestService;

@Configuration
public class ThermosTestServiceConfiguration {

    @Autowired
    private BackendService thermosBackendService;
    @Autowired
    private ThermosHelper thermosHelper;

    @Bean
    public TestService thermosTestService() {
        return thermosHelper.createCircuitBreakerProxy(createTestService(), "failingMethod", "thermosTestService");
    }

    private TestService createTestService() {
        return new ThermosTestService(thermosBackendService);
    }

}
