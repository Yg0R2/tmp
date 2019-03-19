package com.yg0r2.rms.ces.web;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.rms.service.EmailServiceSpammer;

@RestController
public class ConfirmationEmailServiceController {

    @Autowired
    private EmailServiceSpammer cesSpammer;

    @GetMapping(value = "/api/ces/generate", params = "count")
    public ResponseEntity<?> generateRequests(@Valid @Min(1) @RequestParam int count) {
        return ResponseEntity.ok(cesSpammer.spam(count));
    }

}
