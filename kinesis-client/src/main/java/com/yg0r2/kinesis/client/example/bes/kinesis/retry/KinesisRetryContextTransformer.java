package com.yg0r2.kinesis.client.example.bes.kinesis.retry;

import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.RetryContextTransformer;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;

@Component
public class KinesisRetryContextTransformer implements RetryContextTransformer<KinesisMessageRecord> {

    @Override
    public RetryContext transform(KinesisMessageRecord messageRecord, Throwable throwable) {
        return RetryContext.builder()
            .withRequestCreateDateTime(messageRecord.getCreateDateTime())
            .withRequestNextRetryDateTime(messageRecord.getNextRetryDateTime())
            .withRetryCount(messageRecord.getRetryCount())
            .withThrowable(throwable)
            .build();
    }

}
