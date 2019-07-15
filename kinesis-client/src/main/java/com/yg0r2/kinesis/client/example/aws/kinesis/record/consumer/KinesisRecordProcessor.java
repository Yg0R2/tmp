package com.yg0r2.kinesis.client.example.aws.kinesis.record.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.aws.kinesis.record.domain.KinesisRecord;
import com.yg0r2.kinesis.client.example.bes.BookingEmailRequestProcessorService;

@Component
public class KinesisRecordProcessor {

    @Autowired
    private BookingEmailRequestProcessorService bookingEmailRequestProcessorService;

    public void processRecord(KinesisRecord kinesisRecord) {
        bookingEmailRequestProcessorService.processRequest(kinesisRecord.getBookingEmailRequest());
    }

}
