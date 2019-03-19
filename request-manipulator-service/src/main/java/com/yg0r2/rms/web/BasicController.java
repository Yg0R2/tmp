package com.yg0r2.rms.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping(value = "/")
    public List<String> get() {
        return List.of(
            "/api/beres/generate?count={Integer}",
            "/api/bes/generate?count={Integer}",
            "/api/ces/generate?count={Integer}"
        );
    }

}
