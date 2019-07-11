package com.yg0r2.kinesis.client.example.bes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;

@Component
public class IgnoredKinesisRecordLogger implements IgnoredRecordLogger<KinesisRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IgnoredKinesisRecordLogger.class);

    @Override
    public void log(KinesisRecord kinesisRecord) {
        LOGGER.error("{}", kinesisRecord);
    }

}
