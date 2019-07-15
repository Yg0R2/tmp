package com.yg0r2.kinesis.client.example.bes.kinesis.record.serialization;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.kinesis.client.example.bes.kinesis.record.domain.KinesisMessageRecord;

@Component
public class KinesisRecordSerializer {

    private final Object lock = new Object();

    @Autowired
    private ObjectMapper objectMapper;

    public ByteBuffer serialize(KinesisMessageRecord kinesisRecord) {
        byte[] data = getJsonValue(kinesisRecord);

        //synchronized (lock) {
            return ByteBuffer.wrap(data);
        //}
    }

    private byte[] getJsonValue(KinesisMessageRecord kinesisRecord) {
        try {
            return objectMapper.writeValueAsString(kinesisRecord).getBytes("UTF-8");
        }
        catch (JsonProcessingException | UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }

}
