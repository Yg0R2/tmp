package com.yg0r2.kinesis.client.example.bes.kafka.record.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;

@Component
public class KafkaRecordProducer implements RecordProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRecordProducer.class);

    @Override
    public void produce(MessageRecord kafkaMessageRecord) {
        LOGGER.info("Producing record: {}", kafkaMessageRecord);
    }

}
