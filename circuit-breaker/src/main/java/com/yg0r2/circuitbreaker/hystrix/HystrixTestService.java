package com.yg0r2.circuitbreaker.hystrix;

import org.springframework.stereotype.Service;

import com.yg0r2.circuitbreaker.TestService;

@Service
public class HystrixTestService implements TestService {

    //@HystrixCommand//(fallbackMethod = "reliable")
    public String failingMethod() {
        throw new RuntimeException("FAILED");
    }

    public String reliable() {
        return "Fallback";
    }

}
