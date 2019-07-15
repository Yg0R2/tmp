package com.yg0r2.kinesis.client.example.bes.kinesis.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class SlowLaneWorkerThreadPoolTaskExecutorConfiguration {

    @Value("${aws.kinesis.worker.awaitTerminationInSeconds}")
    private int awaitTermination;
    @Value("${aws.kinesis.worker.threadPoolSize}")
    private int threadPoolSize;

    @Bean
    public ThreadPoolTaskExecutor slowLaneWorkerThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setThreadNamePrefix("slowLane-worker-thread-");
        taskExecutor.setCorePoolSize(threadPoolSize);
        taskExecutor.setMaxPoolSize(threadPoolSize);
        taskExecutor.setDaemon(true);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(awaitTermination);

        return taskExecutor;
    }
}
