package com.yg0r2.kinesis.client.example.kinesis.record.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.yg0r2.kinesis.client.example.kinesis.record.KinesisRecordDeserializer;
import com.yg0r2.kinesis.client.example.kinesis.record.consumer.KinesisRecordProcessor;
import com.yg0r2.kinesis.client.example.kinesis.record.consumer.KinesisShardRecordProcessor;

@Configuration
public class FastLaneKinesisShardRecordProcessorConfiguration {

    @Autowired
    private KinesisRecordDeserializer kinesisRecordDeserializer;
    @Autowired
    private KinesisRecordProcessor kinesisRecordProcessor;
    @Autowired
    private ThreadPoolTaskExecutor fastLaneThreadPoolTaskExecutor;

    @Bean
    //@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public KinesisShardRecordProcessor fastLaneKinesisShardRecordProcessor() {
        return new KinesisShardRecordProcessor(kinesisRecordDeserializer, kinesisRecordProcessor, fastLaneThreadPoolTaskExecutor);
    }

}
