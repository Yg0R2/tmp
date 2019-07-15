package com.yg0r2.kinesis.client.example.aws.kinesis.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;

@Configuration
public class KinesisClientConfiguration {

    @Value("${aws.region}")
    private String region;
    @Value("${aws.kinesis.endpoint}")
    private String kinesisEndpoint;

    @Autowired
    private SdkAsyncHttpClient sdkAsyncHttpClient;
    @Autowired
    private AwsCredentialsProvider awsSdkCredentialsProvider;

    @Bean
    public KinesisAsyncClient kinesisAsyncClient() {
        return KinesisAsyncClient.builder()
            .region(Region.of(region))
            .credentialsProvider(awsSdkCredentialsProvider)
            .httpClient(sdkAsyncHttpClient)
            .endpointOverride(URI.create(kinesisEndpoint))
            .build();
    }

}
