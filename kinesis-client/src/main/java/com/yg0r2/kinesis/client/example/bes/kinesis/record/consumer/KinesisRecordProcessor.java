package com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.BookingEmailRequestProcessorService;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProcessor;

@Component
public class KinesisRecordProcessor implements RecordProcessor<KinesisMessageRecord> {

    @Autowired
    private BookingEmailRequestProcessorService bookingEmailRequestProcessorService;

    @Override
    public void processRecord(KinesisMessageRecord kinesisRecord) {
        bookingEmailRequestProcessorService.processRequest(kinesisRecord.getBookingEmailRequest());
    }

}
