package com.yg0r2.kinesis.client.example.messaging.retry;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;

public interface RetryContextTransformer<T extends MessageRecord> {

    RetryContext transform(T messageRecord, Throwable throwable);

}
