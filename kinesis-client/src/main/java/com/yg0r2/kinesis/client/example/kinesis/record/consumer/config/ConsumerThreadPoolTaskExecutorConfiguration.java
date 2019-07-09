package com.yg0r2.kinesis.client.example.kinesis.record.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConsumerThreadPoolTaskExecutorConfiguration {

    @Value("${aws.kinesis.stream.fastLane.awaitTerminationInSeconds}")
    private int awaitTermination;
    @Value("${aws.kinesis.stream.fastLane.threadPoolSize}")
    private int threadPoolSize;

    @Bean
    public ThreadPoolTaskExecutor consumerThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setThreadNamePrefix("fastLane-");
        taskExecutor.setCorePoolSize(threadPoolSize);
        taskExecutor.setMaxPoolSize(threadPoolSize);
        taskExecutor.setDaemon(true);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(awaitTermination);

        return taskExecutor;
    }

}
