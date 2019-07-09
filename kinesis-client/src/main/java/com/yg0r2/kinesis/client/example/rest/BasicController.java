package com.yg0r2.kinesis.client.example.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class BasicController {

    @GetMapping(value = "/")
    public ModelAndView get() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

}
