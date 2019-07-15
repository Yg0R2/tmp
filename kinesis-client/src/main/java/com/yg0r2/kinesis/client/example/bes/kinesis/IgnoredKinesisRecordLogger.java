package com.yg0r2.kinesis.client.example.bes.kinesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.service.IgnoredRecordLogger;

@Component
public class IgnoredKinesisRecordLogger implements IgnoredRecordLogger<KinesisMessageRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IgnoredKinesisRecordLogger.class);

    @Override
    public void log(KinesisMessageRecord kinesisRecord) {
        LOGGER.error("{}", kinesisRecord);
    }

}
