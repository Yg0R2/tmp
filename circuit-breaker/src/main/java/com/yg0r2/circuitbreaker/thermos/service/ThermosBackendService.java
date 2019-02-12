package com.yg0r2.circuitbreaker.thermos.service;

import com.yg0r2.circuitbreaker.BackendService;

public class ThermosBackendService implements BackendService {

    @Override
    public String getData() {
        throw new RuntimeException("FAILED");
    }

}
