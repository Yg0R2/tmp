package com.yg0r2.common.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @GetMapping(value = "/")
    public List<String> get() {
        return List.of(
            "/api/bes/generate?count={Integer}[&orderNumber={Long}&lastName={LAST_NAME}]",
            "/api/ces/generate?count={Integer}"
        );
    }

}
