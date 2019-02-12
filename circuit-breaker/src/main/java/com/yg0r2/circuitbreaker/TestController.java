package com.yg0r2.circuitbreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService hystrixTestService;
    @Autowired
    private TestService thermosTestService;

    @GetMapping("/testHystrix")
    public String testHystrix() {
        return hystrixTestService.failingMethod();
    }

    @GetMapping("/testThermos")
    public String testThermos() {
        return thermosTestService.failingMethod();
    }

}
