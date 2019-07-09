package com.yg0r2.kinesis.client.example.aws.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.yg0r2.kinesis.client.example.kinesis.KinesisShardRecordProcessorFactory;

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

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    ConfigsBuilder configsBuilder() {
        ConfigsBuilder configsBuilder = new ConfigsBuilder(streamName, applicationName, kinesisAsyncClient, dynamoDbAsyncClient,
            cloudWatchAsyncClient, UUID.randomUUID().toString(), new KinesisShardRecordProcessorFactory());

        configsBuilder.tableName(tableName);

        return configsBuilder;
    }

}
