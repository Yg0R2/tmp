package com.yg0r2.kinesis.client.example.bes.retry;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.aws.kinesis.record.domain.KinesisRecord;
import com.yg0r2.kinesis.client.example.aws.kinesis.record.producer.KinesisRecordProducer;
import com.yg0r2.kinesis.client.example.bes.IgnoredRecordLogger;

@Component
public class KinesisRetryService implements RetryService<KinesisRecord> {

    private static final Random RND = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRetryService.class);

    @Autowired
    private KinesisRecordProducer slowLaneKinesisRecordProducer;
    @Autowired
    private IgnoredRecordLogger<KinesisRecord> ignoredKinesisRecordLogger;

    @Override
    public boolean canExecuteRetry(KinesisRecord kinesisRecord) {
        return true;
    }

    @Override
    public void handleFailedRetry(KinesisRecord kinesisRecord, Throwable throwable) {
        if (canRescheduleFailedRetry(kinesisRecord)) {
            LOGGER.info("Resubmit record: {}", kinesisRecord);
            slowLaneKinesisRecordProducer.produce(createUpdatedRecord(kinesisRecord));
        }
        else {
            LOGGER.error("Ignore record: {}", kinesisRecord);
            ignoredKinesisRecordLogger.log(kinesisRecord);
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
