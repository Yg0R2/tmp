package com.yg0r2.kinesis.client.example.rest;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;
import com.yg0r2.kinesis.client.example.bes.domain.ServiceCallContext;
import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;
import com.yg0r2.kinesis.client.example.kinesis.record.producer.KinesisRecordProducer;

@RestController
public class ProducerRestController {

    private static final Logger LOGGER  = LoggerFactory.getLogger(ProducerRestController.class);

    @Autowired
    private KinesisRecordProducer fastLaneKinesisRecordProducer;

    @GetMapping("/api/producer")
    public String startProducing(@RequestParam(defaultValue = "1") @Min(1) int count) {
        LOGGER.info("Scheduled producing records...");

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        for (int i = 0; i < count; i++) {
            scheduledExecutorService.execute(() -> publishRecord());
        }

        return "Scheduled producing records...";
    }

    @PostMapping("/api/producer/single")
    public HttpStatus handleRequest(@Valid @ModelAttribute("context") ServiceCallContext serviceCallContext,
        @Valid @RequestBody BookingEmailRequest bookingEmailRequest) {

        BookingEmailRequest updatedBookingEmailRequest = BookingEmailRequest.builder(bookingEmailRequest)
            .withRequestId(serviceCallContext.getRequestId())
            .build();

        fastLaneKinesisRecordProducer.produce(createKinesisRecord(updatedBookingEmailRequest));

        return HttpStatus.OK;
    }

    @ModelAttribute("context")
    public ServiceCallContext bindServiceCallContext(HttpServletRequest request) {
        UUID requestId = Optional.ofNullable(request.getHeader("Request-Context-Request-Id"))
            .map(UUID::fromString)
            .orElse(UUID.randomUUID());

        return ServiceCallContext.builder()
            .withRequestId(requestId)
            .build();
    }

    private void publishRecord() {
        fastLaneKinesisRecordProducer.produce(createKinesisRecord(createBookingEmailRequest()));
    }

    private KinesisRecord createKinesisRecord(BookingEmailRequest bookingEmailRequest) {
        return KinesisRecord.builder()
            .withBookingEmailRequest(bookingEmailRequest)
            .build();
    }

    private BookingEmailRequest createBookingEmailRequest() {
        return BookingEmailRequest.builder()
            .withRequestId(UUID.randomUUID())
            .build();
    }

}
