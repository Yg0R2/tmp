package com.yg0r2.kinesis.client.example.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ConditionalOnProperty(name = "messaging.implementation", havingValue = "kafka")
@ComponentScan(basePackages = "com.yg0r2.kinesis.client.example",
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.yg0r2.kinesis.client.example.bes.kinesis..*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.yg0r2.kinesis.client.example.aws..*")
})
public class KafkaMessagingConfiguration {

}
