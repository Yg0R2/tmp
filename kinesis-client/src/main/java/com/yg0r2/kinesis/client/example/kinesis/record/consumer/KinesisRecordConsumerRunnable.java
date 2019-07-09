package com.yg0r2.kinesis.client.example.kinesis.record.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;

public class KinesisRecordConsumerRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRecordConsumerRunnable.class);

    private final KinesisRecord kinesisRecord;

    public KinesisRecordConsumerRunnable(KinesisRecord kinesisRecord) {
        this.kinesisRecord = kinesisRecord;
    }

    @Override
    public void run() {
        LOGGER.info("Consuming record: {}", kinesisRecord);

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
        }

        LOGGER.info("Consumed record: {}", kinesisRecord);
    }

}
