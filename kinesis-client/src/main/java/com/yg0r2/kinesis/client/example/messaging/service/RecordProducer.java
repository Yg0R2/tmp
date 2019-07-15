package com.yg0r2.kinesis.client.example.messaging.service;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;

public interface RecordProducer<T extends MessageRecord> {

    void produce(T messageRecord);

}
