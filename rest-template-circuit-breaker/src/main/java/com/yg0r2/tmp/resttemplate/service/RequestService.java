package com.yg0r2.tmp.resttemplate.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.hotels.thermos.spring.annotations.ThermosWrapped;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Component
public class RequestService {

    @Autowired
    private RestTemplate restTemplate;

    @ThermosWrapped(configName = "infiniteService")
    /*@HystrixCommand(groupKey = "infiniteService", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
    })*/
    public String callService() {
        try {
            return restTemplate.getForEntity(new URL("http://localhost:8080/api/infinite").toURI(), String.class).getBody();
        }
        catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
