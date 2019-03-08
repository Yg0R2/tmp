package com.yg0r2.bes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.bes.domain.EmailRequest;
import com.yg0r2.bes.service.EmailRequestFactory;
import com.yg0r2.bes.service.EmailSender;
import com.yg0r2.bes.service.RequestContextFactory;

@RestController
public class RequestGeneratorController {

    @Autowired
    private EmailRequestFactory emailRequestFactory;
    @Autowired
    private RequestContextFactory requestContextFactory;
    @Autowired
    private EmailSender emailSender;

    @GetMapping(value = "/")
    public String get() {
        return "/api/send?count={Integer}[&orderNumber={Long}&lastName={LAST_NAME}]";
    }

    @GetMapping(value = "/api/send", params = "count")
    public ResponseEntity<?> sendEmails(@Valid @Min(1) @RequestParam int count, @Nullable Long orderNumber, @Nullable String lastName) {
            List<String> responses = getRequests(count, orderNumber, lastName).stream()
                .map(emailRequest -> emailSender.sendEmail(emailRequest, requestContextFactory.create()))
                .map(ResponseEntity::getBody)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    private List<EmailRequest> getRequests(int count, Long orderNumber, String lastName) {
        return IntStream.range(0, count)
            .mapToObj(i -> emailRequestFactory.create(orderNumber, lastName))
            .collect(Collectors.toList());
    }

}
