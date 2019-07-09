package com.yg0r2.kinesis.client.example.kinesis.record.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class KinesisShardRecordConsumerFactory implements ShardRecordProcessorFactory {

    @Autowired
    private KinesisShardRecordConsumer kinesisShardRecordConsumer;

    @Override
    public ShardRecordProcessor shardRecordProcessor() {
        return kinesisShardRecordConsumer;
    }

}
