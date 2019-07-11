package com.yg0r2.kinesis.client.example.bes.retry;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;
import com.yg0r2.kinesis.client.example.kinesis.record.producer.KinesisRecordProducer;

@Component
public class KinesisRetryService implements RetryService<KinesisRecord> {

    private static final Random RND = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRetryService.class);

    @Autowired
    private KinesisRecordProducer kinesisRecordProducer;

    @Override
    public boolean canExecuteRetry(KinesisRecord kinesisRecord) {
        return true;
    }

    @Override
    public void handleFailedRetry(KinesisRecord kinesisRecord, Throwable throwable) {
        if (canRescheduleFailedRetry(kinesisRecord)) {
            LOGGER.info("Resubmit record: {}", kinesisRecord);
            kinesisRecordProducer.produce(createUpdatedRecord(kinesisRecord));
        }
        else {
            LOGGER.error("Ignore record: {}", kinesisRecord);
        }
    }

    private boolean canRescheduleFailedRetry(KinesisRecord kinesisRecord) {
        return (kinesisRecord.getRetryCount() < 2) ? true : RND.nextBoolean();
    }

    private KinesisRecord createUpdatedRecord(KinesisRecord kinesisRecord) {
        return KinesisRecord.builder(kinesisRecord)
            .withRetryCount(kinesisRecord.getRetryCount() + 1)
            .build();
    }

}
