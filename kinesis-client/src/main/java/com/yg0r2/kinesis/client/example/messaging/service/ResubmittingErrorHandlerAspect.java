package com.yg0r2.kinesis.client.example.messaging.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.RetryService;

@Aspect
@Component
public class ResubmittingErrorHandlerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResubmittingErrorHandlerAspect.class);

    @Autowired
    private RetryService retryService;
    @Autowired
    private RecordProducer slowLaneRecordProducer;

    @Around("execution(* com.yg0r2.kinesis.client.example.messaging.service.RecordProcessor.processRecord(..))")
    public void executeDefendedMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MessageRecord messageRecord = getMessageRecord(proceedingJoinPoint);

        if (retryService.canExecuteRetry(messageRecord)) {
            try {
                proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                LOGGER.error("Failed to execute...", throwable);

                retryService.handleFailedRetry(messageRecord, throwable);
            }
        }
        else {
            LOGGER.info("Resubmitting currently cannot retryable record: {}", messageRecord);

            slowLaneRecordProducer.produce(messageRecord);
        }
    }

    @SuppressWarnings("unchecked")
    private MessageRecord getMessageRecord(JoinPoint joinPoint) {
        return (MessageRecord) joinPoint.getArgs()[0];
    }

}
