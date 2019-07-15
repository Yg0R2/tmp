package com.yg0r2.kinesis.client.example.messaging.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;
import com.yg0r2.kinesis.client.example.messaging.service.IgnoredRecordLogger;
import com.yg0r2.kinesis.client.example.messaging.service.MessageRecordFactory;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;

public class RetryService<T extends MessageRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryService.class);

    private final RecordProducer<T> recordProducer;
    private final IgnoredRecordLogger<T> ignoredRecordLogger;
    private final RetryContextTransformer<T> retryContextTransformer;
    private final RetryPolicy retryPolicy;
    private final MessageRecordFactory<T> messageRecordFactory;

    public RetryService(RecordProducer<T> recordProducer, IgnoredRecordLogger<T> ignoredRecordLogger, RetryContextTransformer<T> retryContextTransformer,
        RetryPolicy retryPolicy, MessageRecordFactory<T> messageRecordFactory) {

        this.recordProducer = recordProducer;
        this.ignoredRecordLogger = ignoredRecordLogger;
        this.retryContextTransformer = retryContextTransformer;
        this.retryPolicy = retryPolicy;
        this.messageRecordFactory = messageRecordFactory;
    }

    public boolean canExecuteRetry(T messageRecord) {
        RetryContext retryContext = createRetryContext(messageRecord, null);

        return retryPolicy.canExecuteRetry(retryContext);
    }


    public void handleFailedRetry(T messageRecord, Throwable throwable) {
        RetryContext retryContext = createRetryContext(messageRecord, null);

        if (canRescheduleFailedRetry(retryContext)) {
            LOGGER.info("Resubmit record: {}", messageRecord);

            recordProducer.produce(createUpdatedRecord(messageRecord, retryContext));
        }
        else {
            LOGGER.error("Ignore record: {}", messageRecord);

            ignoredRecordLogger.log(messageRecord);
        }
    }

    private RetryContext createRetryContext(T record, Throwable throwable) {
        return retryContextTransformer.transform(record, throwable);
    }

    private boolean canRescheduleFailedRetry(RetryContext retryContext) {
        return retryPolicy.canRescheduleFailedRetry(retryContext);
    }

    private T createUpdatedRecord(T messageRecord, RetryContext retryContext) {
        RetryContext updatedRetryContext = RetryContext.builder(retryContext)
            .withRequestNextRetryDateTime(retryPolicy.getNextRetryDateTime(retryContext))
            .withRetryCount(retryContext.getRetryCount() + 1)
            .build();

        return messageRecordFactory.create(messageRecord, updatedRetryContext);
    }

}
