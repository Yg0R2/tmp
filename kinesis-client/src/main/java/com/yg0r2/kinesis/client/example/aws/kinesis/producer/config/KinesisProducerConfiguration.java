package com.yg0r2.kinesis.client.example.aws.kinesis.producer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.kinesis.producer.KinesisProducer;

@Configuration
public class KinesisProducerConfiguration {

    @Value("${aws.region}")
    private String region;
    @Value("${aws.kinesis.host}")
    private String kinesisHost;
    @Value("${aws.kinesis.port}")
    private long kinesisPort;

    @Value("${aws.kinesis.stream.fastLane.maxConnections}")
    private int fastLaneMaxConnections;

    @Value("${aws.kinesis.stream.slowLane.maxConnections}")
    private int slowLaneMaxConnections;

    @Autowired
    private AWSCredentialsProvider awsCredentialsProvider;

    @Bean
    public KinesisProducer fastLaneKinesisProducer() {
        return new KinesisProducer(createKinesisProducerConfiguration(fastLaneMaxConnections));
    }

    @Bean
    public KinesisProducer slowLaneKinesisProducer() {
        return new KinesisProducer(createKinesisProducerConfiguration(slowLaneMaxConnections));
    }

    private com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration createKinesisProducerConfiguration(long maxConnections) {
        return new com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration()
            .setRegion(region)
            //.setMaxConnections(maxConnections)
            .setCredentialsProvider(awsCredentialsProvider)
            .setKinesisEndpoint(kinesisHost)
            .setKinesisPort(kinesisPort)
            //.setRequestTimeout(100L)
            .setAggregationEnabled(false)
            .setVerifyCertificate(false)
            .setMetricsLevel("none")
            //.setRecordMaxBufferedTime(0L)
            .setRecordTtl(60000L);
    }

}
