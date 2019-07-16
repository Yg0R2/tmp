package com.yg0r2.kinesis.client.example.bes.kafka.record.producer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yg0r2.kinesis.client.example.bes.kafka.record.domain.KafkaMessageRecord;
import com.yg0r2.kinesis.client.example.bes.kafka.record.producer.KafkaRecordProducer;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;

@Configuration
public class FastLaneKafkaRecordProducerConfiguration {

    @Bean
    public RecordProducer<KafkaMessageRecord> fastLaneRecordProducer() {
        return new KafkaRecordProducer();
    }

}
