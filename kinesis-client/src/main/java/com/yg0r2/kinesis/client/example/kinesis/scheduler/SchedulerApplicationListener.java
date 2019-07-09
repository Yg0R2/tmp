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
public class SchedulerApplicationListener implements ApplicationListener<ContextClosedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerApplicationListener.class);

    @Autowired
    private ThreadPoolTaskExecutor workerThreadPoolTaskExecutor;
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init() {
        workerThreadPoolTaskExecutor.execute(scheduler);

        LOGGER.info("Start processing from stream: {}", scheduler.streamName());
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        LOGGER.info("Start graceful shutdown on stream: {}", scheduler.streamName());

        scheduler.startGracefulShutdown();
    }

}
