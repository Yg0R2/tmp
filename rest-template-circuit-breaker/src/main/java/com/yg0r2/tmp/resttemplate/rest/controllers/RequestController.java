package com.yg0r2.tmp.resttemplate.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.tmp.resttemplate.service.RequestService;

@RestController
@RequestMapping("/api/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public String request() {
        return requestService.callService();
    }

}
