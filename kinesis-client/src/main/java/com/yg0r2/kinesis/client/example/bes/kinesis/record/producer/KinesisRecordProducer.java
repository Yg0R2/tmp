package com.yg0r2.kinesis.client.example.bes.kinesis.record.producer;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.kinesis.producer.Attempt;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.UserRecord;
import com.amazonaws.services.kinesis.producer.UserRecordFailedException;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.serialization.KinesisRecordSerializer;
import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;
import com.yg0r2.kinesis.client.example.messaging.service.RecordProducer;

public class KinesisRecordProducer implements RecordProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRecordProducer.class);

    private final String streamName;
    private final KinesisProducer kinesisProducer;
    private final KinesisRecordSerializer kinesisRecordSerializer;

    public KinesisRecordProducer(KinesisProducer kinesisProducer, String streamName, KinesisRecordSerializer kinesisRecordSerializer) {
        this.streamName = streamName;
        this.kinesisProducer = kinesisProducer;
        this.kinesisRecordSerializer = kinesisRecordSerializer;
    }

    @Override
    public void produce(MessageRecord messageRecord) {
        LOGGER.info("Producing record: {}", messageRecord);

        Stream.of(messageRecord)
            .map(this::createUserRecord)
            .map(this::getUserRecordResult)
            .forEach(this::handleResult);
    }

    private UserRecord createUserRecord(MessageRecord messageRecord) {
        return new UserRecord(streamName, messageRecord.getBookingEmailRequest().toString(), kinesisRecordSerializer.serialize(messageRecord));
    }

    private UserRecordResult getUserRecordResult(UserRecord userRecord) {
        UserRecordResult userRecordResult;

        try {
            Future<UserRecordResult> userRecordResultFuture = kinesisProducer.addUserRecord(userRecord);

            userRecordResult = userRecordResultFuture.get();
        }
        catch (InterruptedException | ExecutionException exception) {
            if (exception.getCause() instanceof UserRecordFailedException) {
                userRecordResult = ((UserRecordFailedException) exception.getCause()).getResult();
            }
            else {
                throw new RuntimeException(exception);
            }
        }

        return userRecordResult;
    }

    private void handleResult(UserRecordResult userRecordResult) {
        if (userRecordResult.isSuccessful()) {
            LOGGER.info("Record successfully put to shard: {}", userRecordResult.getShardId());
        }
        else {
            String errorMessages = userRecordResult.getAttempts().stream()
                .filter(Objects::nonNull)
                .map(Attempt::getErrorMessage)
                .collect(Collectors.joining(", "));

            LOGGER.error("Failed to put record to shard: {}, errors: {}", userRecordResult.getShardId(), errorMessages);
        }
    }

}
