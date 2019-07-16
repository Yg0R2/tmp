package com.yg0r2.kinesis.client.example.bes.kinesis.retry;

import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;
import com.yg0r2.kinesis.client.example.messaging.service.MessageRecordFactory;

@Component(value = "messageRecordFactory")
public class KinesisMessageRecordFactory implements MessageRecordFactory<KinesisMessageRecord> {

    @Override
    public KinesisMessageRecord create(BookingEmailRequest bookingEmailRequest) {
        return KinesisMessageRecord.builder()
            .withBookingEmailRequest(bookingEmailRequest)
            .build();
    }

    @Override
    public KinesisMessageRecord create(KinesisMessageRecord kinesisMessageRecord, RetryContext retryContext) {
        return KinesisMessageRecord.builder(kinesisMessageRecord)
            .withNextRetryDateTime(retryContext.getRequestNextRetryDateTime())
            .withRetryCount(retryContext.getRetryCount())
            .build();
    }

}