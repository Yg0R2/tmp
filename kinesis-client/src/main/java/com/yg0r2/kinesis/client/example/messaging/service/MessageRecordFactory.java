package com.yg0r2.kinesis.client.example.messaging.service;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;

public interface MessageRecordFactory<T extends MessageRecord> {

    T create(T messageRecord, RetryContext retryContext);

}
