package com.yg0r2.kinesis.client.example.aws.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

@Configuration
class PollingConfigConfiguration {

    @Value("${aws.kinesis.stream.fastLane.name}")
    private String streamName;

    @Autowired
    private KinesisAsyncClient kinesisAsyncClient;

    @Bean
    PollingConfig pollingConfig() {
        PollingConfig pollingConfig = new PollingConfig(streamName, kinesisAsyncClient);

        pollingConfig.maxRecords(1);

        return pollingConfig;
    }

}
