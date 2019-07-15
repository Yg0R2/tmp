package com.yg0r2.kinesis.client.example.aws.kinesis.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.kinesis.common.ConfigsBuilder;
import software.amazon.kinesis.common.InitialPositionInStream;
import software.amazon.kinesis.common.InitialPositionInStreamExtended;
import software.amazon.kinesis.coordinator.Scheduler;
import software.amazon.kinesis.leases.LeaseManagementConfig;
import software.amazon.kinesis.retrieval.RetrievalConfig;
import software.amazon.kinesis.retrieval.polling.PollingConfig;

@Configuration
public class SlowLaneSchedulerConfiguration {

    @Autowired
    private ConfigsBuilder slowLaneConfigsBuilder;
    @Autowired
    private PollingConfig slowLanePollingConfig;

    @Bean
    public Scheduler slowLaneScheduler() {
        return new Scheduler(
            slowLaneConfigsBuilder.checkpointConfig(),
            slowLaneConfigsBuilder.coordinatorConfig(),
            getLeaseManagementConfig(),
            slowLaneConfigsBuilder.lifecycleConfig(),
            slowLaneConfigsBuilder.metricsConfig(),
            slowLaneConfigsBuilder.processorConfig(),
            getRetrievalConfig()
        );
    }

    private LeaseManagementConfig getLeaseManagementConfig() {
        return slowLaneConfigsBuilder.leaseManagementConfig()
            .initialPositionInStream(createInitialPositionInStreamExtended());
    }

    private RetrievalConfig getRetrievalConfig() {
        return slowLaneConfigsBuilder.retrievalConfig()
            .retrievalSpecificConfig(slowLanePollingConfig)
            .initialPositionInStreamExtended(createInitialPositionInStreamExtended());
    }

    private InitialPositionInStreamExtended createInitialPositionInStreamExtended() {
        return InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON);
    }

}
