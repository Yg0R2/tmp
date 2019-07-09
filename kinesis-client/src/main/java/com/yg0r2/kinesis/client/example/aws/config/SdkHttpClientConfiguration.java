package com.yg0r2.kinesis.client.example.aws.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import software.amazon.awssdk.http.Protocol;
import software.amazon.awssdk.http.SdkHttpConfigurationOption;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.utils.AttributeMap;

@Configuration
class SdkHttpClientConfiguration {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    SdkAsyncHttpClient sdkAsyncHttpClient() {
        return NettyNioAsyncHttpClient.builder()
            .buildWithDefaults(createDefaultAttributeMap());
    }

    private AttributeMap createDefaultAttributeMap() {
        return AttributeMap.builder()
            .put(SdkHttpConfigurationOption.PROTOCOL, Protocol.HTTP1_1)
            .put(SdkHttpConfigurationOption.TRUST_ALL_CERTIFICATES, true)
            .build();
    }

}
