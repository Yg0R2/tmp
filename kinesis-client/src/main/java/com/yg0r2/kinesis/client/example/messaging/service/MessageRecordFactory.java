package com.yg0r2.kinesis.client.example.messaging.service;

import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;
import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;

@Component
public class MessageRecordFactory {

    public MessageRecord create(BookingEmailRequest bookingEmailRequest) {
        return MessageRecord.builder()
            .withBookingEmailRequest(bookingEmailRequest)
            .build();
    }

    public MessageRecord create(MessageRecord messageRecord, RetryContext retryContext) {
        return MessageRecord.builder(messageRecord)
            .withNextRetryDateTime(retryContext.getRequestNextRetryDateTime())
            .withRetryCount(retryContext.getRetryCount())
            .build();
    }

}
