package com.yg0r2.kinesis.client.example.bes.kinesis.record.serialization;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.kinesis.client.example.messaging.domain.MessageRecord;

@Component
public class KinesisRecordSerializer {

    @Autowired
    private ObjectMapper objectMapper;

    public ByteBuffer serialize(MessageRecord messageRecord) {
        byte[] data = getJsonValue(messageRecord);

        return ByteBuffer.wrap(data);
    }

    private byte[] getJsonValue(MessageRecord messageRecord) {
        try {
            return objectMapper.writeValueAsString(messageRecord).getBytes("UTF-8");
        }
        catch (JsonProcessingException | UnsupportedEncodingException exception) {
            throw new RuntimeException(exception);
        }
    }

}
