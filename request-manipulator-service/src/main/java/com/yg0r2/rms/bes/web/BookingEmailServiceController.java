package com.yg0r2.rms.bes.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.rms.bes.service.BookingEmailServiceRequestFactory;
import com.yg0r2.rms.bes.service.BookingEmailService;

@RestController
public class BookingEmailServiceController {

    @Autowired
    private BookingEmailServiceRequestFactory bookingEmailServiceRequestFactory;
    @Autowired
    private BookingEmailService bookingEmailService;

    @GetMapping(value = "/api/bes/generate", params = "count")
    public ResponseEntity<?> generateRequests(@Valid @Min(1) @RequestParam int count) {
        List<String> responses = IntStream.range(0, count)
            .mapToObj(i -> bookingEmailServiceRequestFactory.create())
            .map(emailRequest -> bookingEmailService.sendRequest(emailRequest))
            .map(ResponseEntity::getBody)
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
