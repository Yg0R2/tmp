package com.yg0r2.eress.bes.web;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.eress.service.EmailServiceSpammer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"Booking Email Request Queue Feeder Service"})
@RestController
public class BookingEmailServiceController {

    @Autowired
    private EmailServiceSpammer besSpammer;

    @ApiOperation(value = "Generate Requests")
    @ApiImplicitParam(name = "count", required = true, defaultValue = "1")
    @GetMapping(value = "/api/bes/generate", params = "count")
    public ResponseEntity<?> generateRequests(@Valid @Min(1) @RequestParam int count) {
        return ResponseEntity.ok(besSpammer.spam(count));
    }

}
