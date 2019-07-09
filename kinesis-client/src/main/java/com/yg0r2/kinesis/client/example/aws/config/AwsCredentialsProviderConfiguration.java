package com.yg0r2.kinesis.client.example.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;

@Configuration
class AwsCredentialsProviderConfiguration {

    private static final String ACCESS_KEY_ID = "DummyAccessKey";
    private static final String SECRET_ACCESS_KEY = "DummySecretKey";

    @Bean
    AwsCredentialsProvider awsSdkCredentialsProvider() {
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
    }

    @Bean
    AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
    }

}
