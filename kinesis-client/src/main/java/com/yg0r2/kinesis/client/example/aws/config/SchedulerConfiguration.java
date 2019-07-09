package com.yg0r2.kinesis.client.example.aws.config;

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
public class SchedulerConfiguration {

    @Autowired
    private ConfigsBuilder configsBuilder;
    @Autowired
    private PollingConfig pollingConfig;

    @Bean
    public Scheduler scheduler() {
        return new Scheduler(
            configsBuilder.checkpointConfig(),
            configsBuilder.coordinatorConfig(),
            getLeaseManagementConfig(),
            configsBuilder.lifecycleConfig(),
            configsBuilder.metricsConfig(),
            configsBuilder.processorConfig(),
            getRetrievalConfig()
        );
    }

    private LeaseManagementConfig getLeaseManagementConfig() {
        return configsBuilder.leaseManagementConfig()
            .initialPositionInStream(createInitialPositionInStreamExtended());
    }

    private RetrievalConfig getRetrievalConfig() {
        return configsBuilder.retrievalConfig()
            .retrievalSpecificConfig(pollingConfig)
            .initialPositionInStreamExtended(createInitialPositionInStreamExtended());
    }

    private InitialPositionInStreamExtended createInitialPositionInStreamExtended() {
        return InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON);
    }

}
