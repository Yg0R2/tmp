package com.yg0r2.kinesis.client.example.kinesis.record.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.yg0r2.kinesis.client.example.kinesis.record.consumer.KinesisShardRecordProcessor;
import com.yg0r2.kinesis.client.example.kinesis.record.consumer.KinesisShardRecordProcessorFactory;

@Configuration
public class SlowLaneKinesisShardRecordProcessorFactoryConfiguration {

    @Autowired
    private KinesisShardRecordProcessor slowLaneKinesisShardRecordProcessor;

    @Bean
    //@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public KinesisShardRecordProcessorFactory slowLaneKinesisShardRecordProcessorFactory() {
        return new KinesisShardRecordProcessorFactory(slowLaneKinesisShardRecordProcessor);
    }

}
