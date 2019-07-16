package com.yg0r2.kinesis.client.example.bes.kafka.retry;

import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;
import com.yg0r2.kinesis.client.example.bes.kafka.record.domain.KafkaMessageRecord;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;
import com.yg0r2.kinesis.client.example.messaging.service.MessageRecordFactory;

@Component(value = "messageRecordFactory")
public class KafkaMessageRecordFactory implements MessageRecordFactory<KafkaMessageRecord> {

    @Override
    public KafkaMessageRecord create(BookingEmailRequest bookingEmailRequest) {
        return KafkaMessageRecord.builder()
            .withBookingEmailRequest(bookingEmailRequest)
            .build();
    }

    @Override
    public KafkaMessageRecord create(KafkaMessageRecord kafkaMessageRecord, RetryContext retryContext) {
        return KafkaMessageRecord.builder(kafkaMessageRecord)
            .withNextRetryDateTime(retryContext.getRequestNextRetryDateTime())
            .withRetryCount(retryContext.getRetryCount())
            .build();
    }

}
