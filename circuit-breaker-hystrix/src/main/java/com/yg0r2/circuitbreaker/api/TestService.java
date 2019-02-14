package com.yg0r2.circuitbreaker.api;

public interface TestService {

    String failingMethod();

    String fallbackMethod();

}
