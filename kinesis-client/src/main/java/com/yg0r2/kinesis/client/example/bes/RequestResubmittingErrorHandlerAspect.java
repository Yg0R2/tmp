package com.yg0r2.kinesis.client.example.bes;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.retry.RetryService;
import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;
import com.yg0r2.kinesis.client.example.kinesis.record.producer.KinesisRecordProducer;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class RequestResubmittingErrorHandlerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResubmittingErrorHandlerAspect.class);

    @Autowired
    private RetryService<KinesisRecord> kinesisRetryService;
    @Autowired
    private KinesisRecordProducer kinesisRecordProducer;

    public RequestResubmittingErrorHandlerAspect(RetryService<KinesisRecord> kinesisRetryService, KinesisRecordProducer kinesisRecordProducer) {
        this.kinesisRetryService = kinesisRetryService;
        this.kinesisRecordProducer = kinesisRecordProducer;
    }

    @Around("execution(* com.yg0r2.kinesis.client.example.kinesis.record.consumer.KinesisRecordProcessor.processRecord(..))")
    public void executeDefendedMethod(ProceedingJoinPoint proceedingJoinPoint) {
        KinesisRecord kinesisRecord = getKinesisRecord(proceedingJoinPoint);

        if (kinesisRetryService.canExecuteRetry(kinesisRecord)) {
            try {
                proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                LOGGER.error("Failed to execute...", throwable);

                kinesisRetryService.handleFailedRetry(kinesisRecord, throwable);
            }
        }
        else {
            LOGGER.info("Resubmitting currently cannot retryable record: {}", kinesisRecord);

            kinesisRecordProducer.produce(kinesisRecord);
        }
    }

    private KinesisRecord getKinesisRecord(JoinPoint joinPoint) {
        return (KinesisRecord) joinPoint.getArgs()[0];
    }

}
