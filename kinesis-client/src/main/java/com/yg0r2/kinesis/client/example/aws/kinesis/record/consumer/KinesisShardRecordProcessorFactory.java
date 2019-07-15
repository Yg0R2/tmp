package com.yg0r2.kinesis.client.example.aws.kinesis.record.consumer;

import software.amazon.kinesis.processor.ShardRecordProcessor;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;

public class KinesisShardRecordProcessorFactory implements ShardRecordProcessorFactory {

    private final KinesisShardRecordProcessor kinesisShardRecordProcessor;

    public KinesisShardRecordProcessorFactory(KinesisShardRecordProcessor kinesisShardRecordProcessor) {
        this.kinesisShardRecordProcessor = kinesisShardRecordProcessor;
    }

    @Override
    public ShardRecordProcessor shardRecordProcessor() {
        return kinesisShardRecordProcessor;
    }

}
