package com.yg0r2.kinesis.client.example.bes;

import com.yg0r2.kinesis.client.example.bes.domain.Record;

public interface IgnoredRecordLogger<T extends Record> {

    void log(T record);

}
