package com.yg0r2.kinesis.client.example.aws.kinesis.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class FastLaneWorkerThreadPoolTaskExecutorConfiguration {

    @Value("${aws.kinesis.worker.awaitTerminationInSeconds}")
    private int awaitTermination;

    @Bean
    public ThreadPoolTaskExecutor fastLaneWorkerThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setThreadNamePrefix("fastLane-worker-thread-");
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setDaemon(true);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(awaitTermination);

        return taskExecutor;
    }

}
