package com.yg0r2.kinesis.client.example.messaging.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.retry.RetryService;

@Aspect
public class ResubmittingErrorHandlerAspect<T extends MessageRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResubmittingErrorHandlerAspect.class);

    private final RetryService<T> retryService;
    private final RecordProducer<T> recordProducer;

    public ResubmittingErrorHandlerAspect(RetryService<T> retryService, RecordProducer<T> recordProducer) {
        this.retryService = retryService;
        this.recordProducer = recordProducer;
    }

    @Around("within(com.yg0r2.kinesis.client.example.messaging.service.RecordProcessor+) && execution(* processRecord(..))")
    public void executeDefendedMethod(ProceedingJoinPoint proceedingJoinPoint) {
        T messageRecord = getMessageRecord(proceedingJoinPoint);

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

            recordProducer.produce(messageRecord);
        }
    }

    @SuppressWarnings("unchecked")
    private T getMessageRecord(JoinPoint joinPoint) {
        return (T) joinPoint.getArgs()[0];
    }

}
