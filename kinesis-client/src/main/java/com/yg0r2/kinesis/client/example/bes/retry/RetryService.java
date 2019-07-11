package com.yg0r2.kinesis.client.example.bes.retry;

import com.yg0r2.kinesis.client.example.bes.domain.Record;

public interface RetryService<T extends Record> {

    boolean canExecuteRetry(T record);

    void handleFailedRetry(T record, Throwable throwable);

}
