package com.yg0r2.kinesis.client.example.kinesis.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import software.amazon.kinesis.coordinator.Scheduler;

@Component
public class FastLaneSchedulerApplicationListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastLaneSchedulerApplicationListener.class);

    @Autowired
    private ThreadPoolTaskExecutor fastLaneWorkerThreadPoolTaskExecutor;
    @Autowired
    private Scheduler fastLaneScheduler;

    @PostConstruct
    public void init() {
        fastLaneWorkerThreadPoolTaskExecutor.execute(fastLaneScheduler);

        LOGGER.info("Start processing from stream: {}", fastLaneScheduler.streamName());
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOGGER.info("Start graceful shutdown on stream: {}", fastLaneScheduler.streamName());

        fastLaneScheduler.startGracefulShutdown();
    }

}
