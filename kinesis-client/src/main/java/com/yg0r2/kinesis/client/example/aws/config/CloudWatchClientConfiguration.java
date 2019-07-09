package com.yg0r2.kinesis.client.example.aws.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

@Configuration
class CloudWatchClientConfiguration {

    @Value("${aws.region}")
    private String region;
    @Value("${aws.cloudWatch.endpoint}")
    private String cloudWatchEndpoint;

    @Autowired
    private SdkAsyncHttpClient sdkAsyncHttpClient;
    @Autowired
    private AwsCredentialsProvider awsSdkCredentialsProvider;

    @Bean
    CloudWatchAsyncClient cloudWatchAsyncClient() {
        return CloudWatchAsyncClient.builder()
            .region(Region.of(region))
            .credentialsProvider(awsSdkCredentialsProvider)
            .httpClient(sdkAsyncHttpClient)
            .endpointOverride(URI.create(cloudWatchEndpoint))
            .build();
    }

}
