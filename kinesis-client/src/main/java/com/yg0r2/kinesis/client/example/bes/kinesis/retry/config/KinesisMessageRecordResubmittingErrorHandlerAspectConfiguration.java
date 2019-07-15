package com.yg0r2.kinesis.client.example.bes.kinesis.retry.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.RetryService;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;
import com.yg0r2.kinesis.client.example.messaging.service.ResubmittingErrorHandlerAspect;

@Configuration
public class KinesisMessageRecordResubmittingErrorHandlerAspectConfiguration {

    @Autowired
    private RetryService<KinesisMessageRecord> retryService;
    @Autowired
    private RecordProducer<KinesisMessageRecord> slowLaneKinesisRecordProducer;

    @Bean
    public ResubmittingErrorHandlerAspect<KinesisMessageRecord> kinesisMessageRecordResubmittingErrorHandlerAspect() {
        return new ResubmittingErrorHandlerAspect<>(retryService, slowLaneKinesisRecordProducer);
    }

}
