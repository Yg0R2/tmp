package com.yg0r2.kinesis.client.example.messaging.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.domain.RetryContext;
import com.yg0r2.kinesis.client.example.messaging.service.IgnoredMessageRecordLogger;
import com.yg0r2.kinesis.client.example.messaging.service.MessageRecordFactory;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;

@Component
public class RetryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryService.class);

    @Autowired
    private RecordProducer slowLaneRecordProducer;
    @Autowired
    private IgnoredMessageRecordLogger ignoredMessageRecordLogger;
    @Autowired
    private RetryContextTransformer retryContextTransformer;
    @Autowired
    private RetryPolicy retryPolicy;
    @Autowired
    private MessageRecordFactory messageRecordFactory;

    public boolean canExecuteRetry(MessageRecord messageRecord) {
        RetryContext retryContext = createRetryContext(messageRecord, null);

        return retryPolicy.canExecuteRetry(retryContext);
    }


    public void handleFailedRetry(MessageRecord messageRecord, Throwable throwable) {
        RetryContext retryContext = createRetryContext(messageRecord, null);

        if (canRescheduleFailedRetry(retryContext)) {
            LOGGER.info("Resubmit record: {}", messageRecord);

            slowLaneRecordProducer.produce(createUpdatedRecord(messageRecord, retryContext));
        }
        else {
            LOGGER.error("Ignore record: {}", messageRecord);

            ignoredMessageRecordLogger.log(messageRecord);
        }
    }

    private RetryContext createRetryContext(MessageRecord messageRecord, Throwable throwable) {
        return retryContextTransformer.transform(messageRecord, throwable);
    }

    private boolean canRescheduleFailedRetry(RetryContext retryContext) {
        return retryPolicy.canRescheduleFailedRetry(retryContext);
    }

    private MessageRecord createUpdatedRecord(MessageRecord messageRecord, RetryContext retryContext) {
        RetryContext updatedRetryContext = RetryContext.builder(retryContext)
            .withRequestNextRetryDateTime(retryPolicy.getNextRetryDateTime(retryContext))
            .withRetryCount(retryContext.getRetryCount() + 1)
            .build();

        return messageRecordFactory.create(messageRecord, updatedRetryContext);
    }

}
