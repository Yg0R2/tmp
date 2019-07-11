package com.yg0r2.kinesis.client.example.kinesis.record.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;

public class KinesisRecordConsumerRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRecordConsumerRunnable.class);

    private final KinesisRecord kinesisRecord;
    private final KinesisRecordProcessor kinesisRecordProcessor;

    public KinesisRecordConsumerRunnable(KinesisRecord kinesisRecord, KinesisRecordProcessor kinesisRecordProcessor) {
        this.kinesisRecord = kinesisRecord;
        this.kinesisRecordProcessor = kinesisRecordProcessor;
    }

    @Override
    public void run() {
        LOGGER.info("Consuming record: {}", kinesisRecord);

        kinesisRecordProcessor.processRecord(kinesisRecord);

        LOGGER.info("Consumed record: {}", kinesisRecord);
    }

}
