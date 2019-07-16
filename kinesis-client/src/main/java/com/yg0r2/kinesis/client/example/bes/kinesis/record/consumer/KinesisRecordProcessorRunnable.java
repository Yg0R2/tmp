package com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProcessor;

public class KinesisRecordProcessorRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRecordProcessorRunnable.class);

    private final MessageRecord messageRecord;
    private final RecordProcessor recordProcessor;
    private final String shardId;

    public KinesisRecordProcessorRunnable(MessageRecord messageRecord, RecordProcessor recordProcessor, String shardId) {
        this.messageRecord = messageRecord;
        this.recordProcessor = recordProcessor;
        this.shardId = shardId;
    }

    @Override
    public void run() {
        MDC.put("ShardId", shardId);
        try {
            LOGGER.info("Consuming record: {}", messageRecord);

            recordProcessor.processRecord(messageRecord);

            LOGGER.info("Consumed record: {}", messageRecord);
        }
        finally {
            MDC.remove("ShardId");
        }
    }

}
