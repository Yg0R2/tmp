package com.yg0r2.kinesis.client.example.startup;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;
import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;
import com.yg0r2.kinesis.client.example.kinesis.record.producer.KinesisRecordProducer;

//@Component
public class SampleCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleCommandLineRunner.class);
    private static final int PRODUCER_THREAD_POOL_SIZE = 3;

    @Value("${aws.kinesis.stream.fastLane.name}")
    private String streamName;

    @Autowired
    private KinesisRecordProducer kinesisRecordProducer;

    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService producerExecutor = Executors.newScheduledThreadPool(PRODUCER_THREAD_POOL_SIZE);
        List<ScheduledFuture<?>> producerFutures = startProducers(producerExecutor);

        Thread.sleep(30000L);

        stopProducers(producerFutures, producerExecutor);
    }

    private List<ScheduledFuture<?>> startProducers(ScheduledExecutorService producerExecutor) {
        LOGGER.info("Producing records...");

        return IntStream.range (0, PRODUCER_THREAD_POOL_SIZE)
            .mapToObj(i -> producerExecutor.scheduleAtFixedRate(this::publishRecord, 0, 1 + i, TimeUnit.SECONDS))
            .collect(Collectors.toList());
    }

    private void publishRecord() {
        BookingEmailRequest bookingEmailRequest = BookingEmailRequest.builder()
            .withRequestId(UUID.randomUUID())
            .build();

        KinesisRecord kinesisRecord = KinesisRecord.builder()
            .withBookingEmailRequest(bookingEmailRequest)
            .build();

        kinesisRecordProducer.produce(kinesisRecord);
    }

    private void stopProducers(List<ScheduledFuture<?>> producerFutures, ScheduledExecutorService producerExecutor) {
        LOGGER.info("Stop producers.");

        producerFutures
            .forEach(scheduledFuture -> scheduledFuture.cancel(true));

        producerExecutor.shutdownNow();
    }

}
