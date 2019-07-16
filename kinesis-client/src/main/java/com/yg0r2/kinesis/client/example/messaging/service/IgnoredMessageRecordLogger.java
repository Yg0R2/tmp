package com.yg0r2.kinesis.client.example.messaging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;

@Component
public class IgnoredMessageRecordLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(IgnoredMessageRecordLogger.class);

    public void log(MessageRecord messageRecord) {
        LOGGER.error("{}", messageRecord);
    }

}
