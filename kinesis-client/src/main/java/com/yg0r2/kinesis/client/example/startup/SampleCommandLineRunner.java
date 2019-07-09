package com.yg0r2.kinesis.client.example.startup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;
import software.amazon.kinesis.coordinator.Scheduler;

@Component
public class SampleCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleCommandLineRunner.class);

    @Value("${aws.kinesis.stream.fastLane.name}")
    private String streamName;

    @Autowired
    private KinesisAsyncClient kinesisAsyncClient;
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initialize and start worker");
        Thread schedulerThread = new Thread(scheduler);
        //schedulerThread.setDaemon(true);
        schedulerThread.start();

        LOGGER.info("Publishing records...");
        publishRecords();
        //executeShutDown();
    }

    private void publishRecords() {
        ScheduledExecutorService producerExecutor = Executors.newScheduledThreadPool(2);//newSingleThreadScheduledExecutor();
        ScheduledFuture<?> producerFuture = producerExecutor.scheduleAtFixedRate(this::publishRecord, 10, 1, TimeUnit.SECONDS);

        /*try {
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
        }

        LOGGER.info("Cancelling producer, and shutting down executor.");
        producerFuture.cancel(true);
        producerExecutor.shutdownNow();*/
    }

    private void executeShutDown() {
        Future<Boolean> gracefulShutdownFuture = scheduler.startGracefulShutdown();
        LOGGER.info("Waiting up to 20 seconds for shutdown to complete.");
        try {
            gracefulShutdownFuture.get(20, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            LOGGER.info("Interrupted while waiting for graceful shutdown. Continuing.");
        }
        catch (ExecutionException e) {
            LOGGER.error("Exception while executing graceful shutdown.", e);
        }
        catch (TimeoutException e) {
            LOGGER.error("Timeout while waiting for shutdown. Scheduler may not have exited.");
        }

        LOGGER.info("Completed, shutting down now.");
    }

    private void publishRecord() {
        PutRecordRequest putRecordRequest = PutRecordRequest.builder()
                .partitionKey(RandomStringUtils.randomAlphabetic(5, 20))
                .streamName(streamName)
                .data(SdkBytes.fromByteArray(RandomUtils.nextBytes(10)))
                .build();

        LOGGER.info("Put record: {}", putRecordRequest);

        try {
            kinesisAsyncClient.putRecord(putRecordRequest).get();
        } catch (InterruptedException e) {
            LOGGER.info("Interrupted, assuming shutdown.");
        } catch (ExecutionException e) {
            LOGGER.error("Exception while sending data to Kinesis. Will try again next cycle.", e);
        }
    }

}
