package com.yg0r2.rms.ces.web;

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

import com.hotels.services.eventservice.confemail.response.EventServiceConfirmationEmailResponse;
import com.yg0r2.rms.ces.domain.ConfirmationEmailServiceRequest;
import com.yg0r2.rms.domain.EmailResponse;
import com.yg0r2.rms.service.EmailService;
import com.yg0r2.rms.service.EmailServiceRequestFactory;

@RestController
public class ConfirmationEmailServiceController {

    @Autowired
    private EmailServiceRequestFactory<ConfirmationEmailServiceRequest> confirmationEmailServiceRequestFactory;
    @Autowired
    private EmailService<ConfirmationEmailServiceRequest, EventServiceConfirmationEmailResponse> confirmationEmailService;

    @GetMapping(value = "/api/ces/generate", params = "count")
    public ResponseEntity<?> generateRequests(@Valid @Min(1) @RequestParam int count) {
        List<EmailResponse> responses = IntStream.range(0, count)
            .mapToObj(i -> confirmationEmailServiceRequestFactory.create())
            .map(confirmationEmailService::sendRequest)
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

}
