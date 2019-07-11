package com.yg0r2.kinesis.client.example.bes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yg0r2.kinesis.client.example.bes.RequestResubmittingErrorHandlerAspect;
import com.yg0r2.kinesis.client.example.bes.retry.RetryService;
import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;
import com.yg0r2.kinesis.client.example.kinesis.record.producer.KinesisRecordProducer;

//@Configuration
public class RequestResubmittingErrorHandlerAspectConfiguration {

    /*@Autowired
    private RetryService<KinesisRecord> kinesisRetryService;
    @Autowired
    private KinesisRecordProducer kinesisRecordProducer;

    @Bean
    public RequestResubmittingErrorHandlerAspect requestResubmittingErrorHandlerAspect() {
        return new RequestResubmittingErrorHandlerAspect(kinesisRetryService, kinesisRecordProducer);
    }*/

}
