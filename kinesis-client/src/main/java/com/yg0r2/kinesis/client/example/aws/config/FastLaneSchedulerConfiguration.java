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
public class FastLaneSchedulerConfiguration {

    @Autowired
    private ConfigsBuilder fastLaneConfigsBuilder;
    @Autowired
    private PollingConfig fastLanePollingConfig;

    @Bean
    public Scheduler fastLaneScheduler() {
        return new Scheduler(
            fastLaneConfigsBuilder.checkpointConfig(),
            fastLaneConfigsBuilder.coordinatorConfig(),
            getLeaseManagementConfig(),
            fastLaneConfigsBuilder.lifecycleConfig(),
            fastLaneConfigsBuilder.metricsConfig(),
            fastLaneConfigsBuilder.processorConfig(),
            getRetrievalConfig()
        );
    }

    private LeaseManagementConfig getLeaseManagementConfig() {
        return fastLaneConfigsBuilder.leaseManagementConfig()
            .initialPositionInStream(createInitialPositionInStreamExtended());
    }

    private RetrievalConfig getRetrievalConfig() {
        return fastLaneConfigsBuilder.retrievalConfig()
            .retrievalSpecificConfig(fastLanePollingConfig)
            .initialPositionInStreamExtended(createInitialPositionInStreamExtended());
    }

    private InitialPositionInStreamExtended createInitialPositionInStreamExtended() {
        return InitialPositionInStreamExtended.newInitialPosition(InitialPositionInStream.TRIM_HORIZON);
    }

}
