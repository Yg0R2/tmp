package com.yg0r2.kinesis.client.example.aws.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yg0r2.kinesis.client.example.kinesis.record.consumer.KinesisShardRecordConsumerFactory;

import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.common.ConfigsBuilder;

@Configuration
class ConfigsBuilderConfiguration {

    @Value("${application.name}")
    private String applicationName;
    @Value("${aws.kinesis.stream.fastLane.name}")
    private String streamName;
    @Value("${aws.dynamoDB.stream.fastLane.tableName}")
    private String tableName;

    @Autowired
    private KinesisAsyncClient kinesisAsyncClient;
    @Autowired
    private DynamoDbAsyncClient dynamoDbAsyncClient;
    @Autowired
    private CloudWatchAsyncClient cloudWatchAsyncClient;
    @Autowired
    private KinesisShardRecordConsumerFactory kinesisShardRecordConsumerFactory;

    @Bean
    ConfigsBuilder configsBuilder() {
        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, applicationName, kinesisAsyncClient, dynamoDbAsyncClient,
            cloudWatchAsyncClient, UUID.randomUUID().toString(), kinesisShardRecordConsumerFactory);

        configsBuilder.tableName(tableName);

        return configsBuilder;
    }

}
