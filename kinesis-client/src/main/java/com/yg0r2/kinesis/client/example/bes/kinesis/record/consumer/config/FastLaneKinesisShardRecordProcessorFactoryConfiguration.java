package com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer.KinesisShardRecordProcessor;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer.KinesisShardRecordProcessorFactory;

@Configuration
public class FastLaneKinesisShardRecordProcessorFactoryConfiguration {

    @Autowired
    private KinesisShardRecordProcessor fastLaneKinesisShardRecordProcessor;

    @Bean
    public KinesisShardRecordProcessorFactory fastLaneKinesisShardRecordProcessorFactory() {
        return new KinesisShardRecordProcessorFactory(fastLaneKinesisShardRecordProcessor);
    }

}
