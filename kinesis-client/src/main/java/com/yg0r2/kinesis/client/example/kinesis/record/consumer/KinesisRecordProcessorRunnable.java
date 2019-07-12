package com.yg0r2.kinesis.client.example.kinesis.record.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;

public class KinesisRecordProcessorRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRecordProcessorRunnable.class);

    private final KinesisRecord kinesisRecord;
    private final KinesisRecordProcessor kinesisRecordProcessor;
    private final String shardId;

    public KinesisRecordProcessorRunnable(KinesisRecord kinesisRecord, KinesisRecordProcessor kinesisRecordProcessor, String shardId) {
        this.kinesisRecord = kinesisRecord;
        this.kinesisRecordProcessor = kinesisRecordProcessor;
        this.shardId = shardId;
    }

    @Override
    public void run() {
        MDC.put("ShardId", shardId);
        try {
            LOGGER.info("Consuming record: {}", kinesisRecord);

            kinesisRecordProcessor.processRecord(kinesisRecord);

            LOGGER.info("Consumed record: {}", kinesisRecord);
        }
        finally {
            MDC.remove("ShardId");
        }
    }

}
