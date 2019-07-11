package com.yg0r2.kinesis.client.example.kinesis.record.producer;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.producer.Attempt;
import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.UserRecord;
import com.amazonaws.services.kinesis.producer.UserRecordFailedException;
import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.yg0r2.kinesis.client.example.kinesis.record.KinesisRecordSerializer;
import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;

@Component
public class KinesisRecordProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KinesisRecordProducer.class);

    @Value("${aws.kinesis.stream.fastLane.name}")
    private String streamName;

    @Autowired
    private KinesisProducer kinesisProducer;
    @Autowired
    private KinesisRecordSerializer kinesisRecordSerializer;

    public void produce(KinesisRecord kinesisRecord) {
        LOGGER.info("Producing record: {}", kinesisRecord);

        Stream.of(kinesisRecord)
            .map(this::createUserRecord)
            .map(this::getUserRecordResult)
            .forEach(this::handleResult);
    }

    private UserRecord createUserRecord(KinesisRecord kinesisRecord) {
        return new UserRecord(streamName, kinesisRecord.getBookingEmailRequest().toString(), kinesisRecordSerializer.serialize(kinesisRecord));
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
