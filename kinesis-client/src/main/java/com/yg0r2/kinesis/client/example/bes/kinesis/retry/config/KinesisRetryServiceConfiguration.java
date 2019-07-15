package com.yg0r2.kinesis.client.example.bes.kinesis.retry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.RetryContextTransformer;
import com.yg0r2.kinesis.client.example.messaging.retry.RetryPolicy;
import com.yg0r2.kinesis.client.example.messaging.retry.RetryService;
import com.yg0r2.kinesis.client.example.messaging.service.IgnoredRecordLogger;
import com.yg0r2.kinesis.client.example.messaging.service.MessageRecordFactory;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;

@Configuration
public class KinesisRetryServiceConfiguration {

    @Autowired
    private RecordProducer<KinesisMessageRecord> slowLaneKinesisRecordProducer;
    @Autowired
    private IgnoredRecordLogger<KinesisMessageRecord> ignoredKinesisRecordLogger;
    @Autowired
    private RetryContextTransformer<KinesisMessageRecord> kinesisRetryContextTransformer;
    @Autowired
    private RetryPolicy retryPolicy;
    @Autowired
    private MessageRecordFactory<KinesisMessageRecord> kinesisMessageRecordFactory;

    @Bean
    public RetryService<KinesisMessageRecord> kinesisRetryService() {
        return new RetryService<>(slowLaneKinesisRecordProducer, ignoredKinesisRecordLogger, kinesisRetryContextTransformer, retryPolicy, kinesisMessageRecordFactory);
    }

}
