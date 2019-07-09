package com.yg0r2.kinesis.client.example.startup;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.CreateStreamRequest;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamRequest;
import software.amazon.awssdk.services.kinesis.model.DescribeStreamResponse;
import software.amazon.awssdk.services.kinesis.model.ResourceInUseException;
import software.amazon.awssdk.services.kinesis.model.StreamStatus;

@Order(0)
@Component
public class KinesisStreamInitializerCommandLineRunner implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisStreamInitializerCommandLineRunner.class);

    @Value("${aws.kinesis.stream.startup.timeout.ms}")
    private long startupTimeout;

    @Value("${aws.kinesis.stream.fastLane.name}")
    private String fastLaneStreamName;
    @Value("${aws.kinesis.stream.fastLane.shardCount}")
    private int fastLaneShardCount;

    @Value("${aws.kinesis.stream.slowLane.name}")
    private String slowLaneStreamName;
    @Value("${aws.kinesis.stream.slowLane.shardCount}")
    private int slowLaneShardCount;

    @Autowired
    private KinesisAsyncClient kinesisAsyncClient;

    @Override
    public void run(String... args) throws Exception {
        createStream(createCreateStreamRequest(fastLaneStreamName, fastLaneShardCount));

        createStream(createCreateStreamRequest(slowLaneStreamName, slowLaneShardCount));
    }

    private CreateStreamRequest createCreateStreamRequest(String streamName, int shardCount) {
        return CreateStreamRequest.builder()
            .streamName(streamName)
            .shardCount(shardCount)
            .build();
    }

    private void createStream(CreateStreamRequest createStreamRequest) throws InterruptedException {
        try {
            kinesisAsyncClient.createStream(createStreamRequest);
        }
        catch (ResourceInUseException exception) {
            LOGGER.info("Stream already exist with name: {}", createStreamRequest.streamName());
        }

        waitForCreateStream(createStreamRequest);
    }

    private void waitForCreateStream(CreateStreamRequest createStreamRequest) throws InterruptedException {
        long start = System.currentTimeMillis();
        long end = start + startupTimeout;

        while (true) {
            Thread.sleep(2000);

            DescribeStreamRequest describeStreamRequest = createDescribeStreamRequest(createStreamRequest);
            CompletableFuture<DescribeStreamResponse> describeStreamResponse = kinesisAsyncClient.describeStream(describeStreamRequest);

            if (StreamStatus.ACTIVE == getStreamStatus(describeStreamResponse)) {
                break;
            }

            Thread.sleep(100);

            if (System.currentTimeMillis() > end) {
                throw new RuntimeException("Stream startup timeout reached.");
            }
        }
    }

    private DescribeStreamRequest createDescribeStreamRequest(CreateStreamRequest createStreamRequest) {
        return DescribeStreamRequest.builder()
            .streamName(createStreamRequest.streamName())
            .build();
    }

    private StreamStatus getStreamStatus(CompletableFuture<DescribeStreamResponse> describeStreamResponse) {
        try {
            return describeStreamResponse.get().streamDescription().streamStatus();
        }
        catch (InterruptedException | ExecutionException exception) {
            throw new RuntimeException("Unable to get stream status.", exception);
        }
    }

}
