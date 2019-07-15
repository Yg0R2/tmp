package com.yg0r2.kinesis.client.example.bes.kinesis.consumer.scheduler;

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
public class SlowLaneSchedulerApplicationListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlowLaneSchedulerApplicationListener.class);

    @Autowired
    private ThreadPoolTaskExecutor slowLaneWorkerThreadPoolTaskExecutor;
    @Autowired
    private Scheduler slowLaneScheduler;

    @PostConstruct
    public void init() {
        slowLaneWorkerThreadPoolTaskExecutor.execute(slowLaneScheduler);

        LOGGER.info("Start processing from stream: {}", slowLaneScheduler.streamName());
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOGGER.info("Start graceful shutdown on stream: {}", slowLaneScheduler.streamName());

        slowLaneScheduler.startGracefulShutdown();
    }

}
