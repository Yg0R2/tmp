package com.yg0r2.kinesis.client.example.bes.kinesis.record.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.yg0r2.kinesis.client.example.bes.kinesis.record.serialization.KinesisRecordDeserializer;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProcessor;

import software.amazon.kinesis.exceptions.InvalidStateException;
import software.amazon.kinesis.exceptions.ShutdownException;
import software.amazon.kinesis.lifecycle.events.InitializationInput;
import software.amazon.kinesis.lifecycle.events.LeaseLostInput;
import software.amazon.kinesis.lifecycle.events.ProcessRecordsInput;
import software.amazon.kinesis.lifecycle.events.ShardEndedInput;
import software.amazon.kinesis.lifecycle.events.ShutdownRequestedInput;
import software.amazon.kinesis.processor.ShardRecordProcessor;

public class KinesisShardRecordProcessor implements ShardRecordProcessor {

    private static final String SHARD_ID_MDC_KEY = "ShardId";
    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisShardRecordProcessor.class);

    private final KinesisRecordDeserializer kinesisRecordDeserializer;
    private final RecordProcessor recordProcessor;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private String shardId;

    public KinesisShardRecordProcessor(KinesisRecordDeserializer kinesisRecordDeserializer, RecordProcessor recordProcessor, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.kinesisRecordDeserializer = kinesisRecordDeserializer;
        this.recordProcessor = recordProcessor;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void initialize(InitializationInput initializationInput) {
        shardId = initializationInput.shardId();

        MDC.put(SHARD_ID_MDC_KEY, shardId);

        try {
            LOGGER.info("Initializing @ Sequence: {}", initializationInput.extendedSequenceNumber());
        }
        finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);

        try {
            LOGGER.info("Consuming {} record(s)", processRecordsInput.records().size());

            processRecordsInput.records().stream()
                .map(kinesisRecordDeserializer::deserialize)
                .map(kinesisRecord -> new KinesisRecordProcessorRunnable(kinesisRecord, recordProcessor, shardId))
                .forEach(threadPoolTaskExecutor::execute);

            processRecordsInput.checkpointer().checkpoint();
        }
        catch (Throwable t) {
            LOGGER.error("Caught throwable while processing records. Aborting.");
            Runtime.getRuntime().halt(1);
        }
        finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    @Override
    public void leaseLost(LeaseLostInput leaseLostInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);

        try {
            LOGGER.info("Lost lease, so terminating.");
        }
        finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    @Override
    public void shardEnded(ShardEndedInput shardEndedInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);

        try {
            LOGGER.info("Reached shard end checkpointing.");
            shardEndedInput.checkpointer().checkpoint();
        }
        catch (ShutdownException | InvalidStateException e) {
            LOGGER.error("Exception while checkpointing at shard end. Giving up.", e);
        }
        finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

    @Override
    public void shutdownRequested(ShutdownRequestedInput shutdownRequestedInput) {
        MDC.put(SHARD_ID_MDC_KEY, shardId);

        try {
            LOGGER.info("Scheduler is shutting down, checkpointing.");
            shutdownRequestedInput.checkpointer().checkpoint();
        }
        catch (ShutdownException | InvalidStateException e) {
            LOGGER.error("Exception while checkpointing at requested shutdown. Giving up.", e);
        }
        finally {
            MDC.remove(SHARD_ID_MDC_KEY);
        }
    }

}
