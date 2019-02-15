package com.yg0r2.circuitbreaker;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.circuitbreaker.api.TestService;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    private static final String TEST_SERVICE1_KEY = "thermosTest1Service";
    private static final String TEST_SERVICE2_KEY = "thermosTest2Service";

    private static Map<String, Integer> COUNTERS_MAP = new HashMap<>();

    @Autowired
    private TestService thermosTest1Service;
    @Autowired
    private TestService thermosTest2Service;

    @GetMapping("/test1")
    public String getThermosTest1Service() {
        COUNTERS_MAP.put(TEST_SERVICE1_KEY, COUNTERS_MAP.getOrDefault(TEST_SERVICE1_KEY, 0) + 1);

        LOGGER.info("Test1Controller call count: {}", COUNTERS_MAP.get(TEST_SERVICE1_KEY));

        return thermosTest1Service.processRequest();
    }

    @GetMapping("/test2")
    public String getThermosTest2Service() {
        COUNTERS_MAP.put(TEST_SERVICE2_KEY, COUNTERS_MAP.getOrDefault(TEST_SERVICE2_KEY, 0) + 1);

        LOGGER.info("Test2Controller call count: {}", COUNTERS_MAP.get(TEST_SERVICE2_KEY));

        return thermosTest2Service.processRequest();
    }

}
