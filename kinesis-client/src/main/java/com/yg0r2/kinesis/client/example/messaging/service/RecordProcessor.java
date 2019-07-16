package com.yg0r2.kinesis.client.example.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.BookingEmailRequestProcessorService;
import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;

@Component
public class RecordProcessor {

    @Autowired
    private BookingEmailRequestProcessorService bookingEmailRequestProcessorService;

    public void processRecord(MessageRecord messageRecord) {
        bookingEmailRequestProcessorService.processRequest(messageRecord.getBookingEmailRequest());
    }

}
