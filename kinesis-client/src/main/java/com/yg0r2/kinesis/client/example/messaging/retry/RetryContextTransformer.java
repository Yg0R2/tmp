package com.yg0r2.kinesis.client.example.messaging.retry;

import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;

@Component
public class RetryContextTransformer {

    public RetryContext transform(MessageRecord messageRecord, Throwable throwable) {
        return RetryContext.builder()
            .withRequestCreateDateTime(messageRecord.getCreateDateTime())
            .withRequestNextRetryDateTime(messageRecord.getNextRetryDateTime())
            .withRetryCount(messageRecord.getRetryCount())
            .withThrowable(throwable)
            .build();
    }

}
