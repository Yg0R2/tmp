package com.yg0r2.kinesis.client.example.aws.kinesis.record.serialization;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.kinesis.client.example.aws.kinesis.record.domain.KinesisRecord;

import software.amazon.kinesis.retrieval.KinesisClientRecord;

@Component
public class KinesisRecordDeserializer {

    @Autowired
    private ObjectMapper objectMapper;

    public KinesisRecord deserialize(KinesisClientRecord kinesisClientRecord) {
        try {
            return objectMapper.readValue(getBytes(kinesisClientRecord), KinesisRecord.class);
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private byte[] getBytes(KinesisClientRecord record) {
        byte[] recordDataBuffer = new byte[record.data().remaining()];

        record.data().get(recordDataBuffer);

        return recordDataBuffer;
    }

}
