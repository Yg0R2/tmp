package com.yg0r2.kinesis.client.example.kinesis.record;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.kinesis.client.example.kinesis.record.domain.KinesisRecord;

@Component
public class KinesisRecordSerializer {

    private final Object lock = new Object();

    @Autowired
    private ObjectMapper objectMapper;

    public ByteBuffer serialize(KinesisRecord kinesisRecord) {
        byte[] data = getJsonValue(kinesisRecord);

        synchronized (lock) {
            return ByteBuffer.wrap(data);
        }
    }

    private byte[] getJsonValue(KinesisRecord kinesisRecord) {
        try {
            return objectMapper.writeValueAsString(kinesisRecord).getBytes("UTF-8");
        }
        catch (JsonProcessingException | UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }

}
