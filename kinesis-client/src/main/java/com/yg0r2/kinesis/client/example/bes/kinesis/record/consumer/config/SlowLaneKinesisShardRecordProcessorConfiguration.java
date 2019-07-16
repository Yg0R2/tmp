package com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer.KinesisShardRecordProcessor;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.serialization.KinesisRecordDeserializer;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProcessor;

@Configuration
public class SlowLaneKinesisShardRecordProcessorConfiguration {

    @Autowired
    private KinesisRecordDeserializer kinesisRecordDeserializer;
    @Autowired
    private RecordProcessor recordProcessor;
    @Autowired
    private ThreadPoolTaskExecutor slowLaneThreadPoolTaskExecutor;

    @Bean
    public KinesisShardRecordProcessor slowLaneKinesisShardRecordProcessor() {
        return new KinesisShardRecordProcessor(kinesisRecordDeserializer, recordProcessor, slowLaneThreadPoolTaskExecutor);
    }

}
