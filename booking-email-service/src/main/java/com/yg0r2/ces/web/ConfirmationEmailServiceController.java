package com.yg0r2.ces.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.ces.domain.ConfirmationEmailRequest;
import com.yg0r2.ces.service.ConfirmationEmailRequestFactory;
import com.yg0r2.ces.service.ConfirmationEmailService;
import com.yg0r2.common.domain.RequestContext;
import com.yg0r2.common.service.RequestContextFactory;

@RestController
public class ConfirmationEmailServiceController {

    @Autowired
    private ConfirmationEmailRequestFactory confirmationEmailRequestFactory;
    @Autowired
    private ConfirmationEmailService confirmationEmailService;
    @Autowired
    private RequestContextFactory requestContextFactory;

    @GetMapping(value = "/api/ces/generate", params = "count")
    public ResponseEntity<?> sendEmails(@Valid @Min(1) @RequestParam int count) {
        List<?> responses = IntStream.range(0, count)
            .mapToObj(i -> requestContextFactory.create())
            .map(this::sendEmail)
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    private HttpStatus sendEmail(RequestContext requestContext) {
        ConfirmationEmailRequest confirmationEmailRequest = confirmationEmailRequestFactory.create(requestContext);

        return confirmationEmailService.sendRequest(confirmationEmailRequest, requestContext);
    }


}
