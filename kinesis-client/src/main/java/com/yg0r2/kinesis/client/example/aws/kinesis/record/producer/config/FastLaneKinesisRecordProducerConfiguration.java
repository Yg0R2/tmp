package com.yg0r2.kinesis.client.example.aws.kinesis.record.producer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.yg0r2.kinesis.client.example.aws.kinesis.record.producer.KinesisRecordProducer;
import com.yg0r2.kinesis.client.example.aws.kinesis.record.serialization.KinesisRecordSerializer;

@Configuration
public class FastLaneKinesisRecordProducerConfiguration {

    @Value("${aws.kinesis.stream.fastLane.name}")
    private String streamName;

    @Autowired
    private KinesisProducer fastLaneKinesisProducer;
    @Autowired
    private KinesisRecordSerializer kinesisRecordSerializer;

    @Bean
    public KinesisRecordProducer fastLaneKinesisRecordProducer() {
        return new KinesisRecordProducer(fastLaneKinesisProducer, streamName, kinesisRecordSerializer);
    }

}
