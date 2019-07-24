package com.yg0r2.tmp.resttemplate.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.tmp.resttemplate.service.InfiniteService;

@RestController
@RequestMapping("/api/infinite")
public class InfiniteController {

    @Autowired
    private InfiniteService infiniteService;

    @GetMapping
    public String infinite() {
        return infiniteService.getData();
    }

}
