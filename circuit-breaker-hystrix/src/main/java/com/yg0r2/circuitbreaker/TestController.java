package com.yg0r2.circuitbreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.circuitbreaker.api.TestService;

@RestController
public class TestController {

    @Autowired
    private TestService hystrixTestService;

    @GetMapping("/test")
    public String testHystrix() {
        return hystrixTestService.failingMethod();
    }

}
