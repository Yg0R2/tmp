package com.yg0r2.kinesis.client.example.bes.kafka.record.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.kinesis.client.example.bes.kafka.record.domain.KafkaMessageRecord;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;

public class KafkaRecordProducer implements RecordProducer<KafkaMessageRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRecordProducer.class);

    @Override
    public void produce(KafkaMessageRecord kafkaMessageRecord) {
        LOGGER.info("Producing record: {}", kafkaMessageRecord);
    }

}
