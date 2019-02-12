package com.yg0r2.circuitbreaker.thermos.service;

import com.hotels.thermos.spring.annotations.ThermosWrapped;
import com.yg0r2.circuitbreaker.TestService;
import com.yg0r2.circuitbreaker.BackendService;

public class ThermosTestService implements TestService {

    private BackendService backendService;

    public ThermosTestService(BackendService backendService) {
        this.backendService = backendService;
    }

    @ThermosWrapped(configName = "thermosTest")
    public String failingMethod() {
        return backendService.getData();
    }

    public String reliable() {
        return "Fallback";
    }

}
