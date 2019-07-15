package com.yg0r2.kinesis.client.example.messaging.retry;

import java.time.LocalDateTime;

import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;

public interface RetryPolicy {

    boolean canExecuteRetry(RetryContext retryContext);

    boolean canRescheduleFailedRetry(RetryContext retryContext);

    LocalDateTime getNextRetryDateTime(RetryContext retryContext);

}
