package com.yg0r2.kinesis.client.example.bes.kinesis.consumer.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.processor.ShardRecordProcessorFactory;

@Configuration
class SlowLaneConfigsBuilderConfiguration {

    @Value("${application.name}")
    private String applicationName;
    @Value("${aws.kinesis.stream.slowLane.name}")
    private String streamName;
    @Value("${aws.dynamoDB.stream.slowLane.tableName}")
    private String tableName;

    @Autowired
    private KinesisAsyncClient kinesisAsyncClient;
    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;
    @Autowired
    private CloudWatchAsyncClient cloudWatchAsyncClient;
    @Autowired
    private ShardRecordProcessorFactory slowLaneKinesisShardRecordProcessorFactory;

    @Bean
    ConfigsBuilder slowLaneConfigsBuilder() {
        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, applicationName, kinesisAsyncClient, dynamoDbAsyncClient,
            cloudWatchAsyncClient, UUID.randomUUID().toString(), slowLaneKinesisShardRecordProcessorFactory);

        configsBuilder.tableName(tableName);

        return configsBuilder;
    }

}
