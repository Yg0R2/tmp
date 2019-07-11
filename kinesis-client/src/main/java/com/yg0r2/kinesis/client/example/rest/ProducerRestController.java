package com.yg0r2.kinesis.client.example.rest;

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

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private KinesisRecordProducer kinesisRecordProducer;

    @GetMapping("/api/producer")
    public String startProducing(@RequestParam(defaultValue = "1") @Min(1) int count) {
        LOGGER.info("Scheduled producing records...");

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

        kinesisRecordProducer.produce(createKinesisRecord(updatedBookingEmailRequest));

        return HttpStatus.OK;
    }

    @ModelAttribute("context")
    public ServiceCallContext bindServiceCallContext(HttpServletRequest request) {
        return ServiceCallContext.builder()
            .withRequestId(UUID.fromString(request.getHeader("Request-Context-Request-Id")))
            .build();
    }

    private void publishRecord() {
        kinesisRecordProducer.produce(createKinesisRecord(createBookingEmailRequest()));
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
