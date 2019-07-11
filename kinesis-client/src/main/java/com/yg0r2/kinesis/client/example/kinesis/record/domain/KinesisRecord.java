package com.yg0r2.kinesis.client.example.kinesis.record.domain;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;
import com.yg0r2.kinesis.client.example.bes.domain.Record;

@JsonDeserialize(builder = KinesisRecord.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class KinesisRecord implements Record {

    private final BookingEmailRequest bookingEmailRequest;
    private final int retryCount;

    private KinesisRecord(Builder builder) {
        bookingEmailRequest = builder.bookingEmailRequest;
        retryCount = Optional.ofNullable(builder.retryCount).orElse(0);
    }

    public BookingEmailRequest getBookingEmailRequest() {
        return bookingEmailRequest;
    }

    public int getRetryCount() {
        return retryCount;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(KinesisRecord kinesisRecord) {
        return new Builder(kinesisRecord);
    }

    public static class Builder {

        private BookingEmailRequest bookingEmailRequest;
        private Integer retryCount;

        private Builder() {
        }

        private Builder(KinesisRecord kinesisRecord) {
            bookingEmailRequest = kinesisRecord.getBookingEmailRequest();
            retryCount = kinesisRecord.getRetryCount();
        }

        public Builder withBookingEmailRequest(BookingEmailRequest bookingEmailRequest) {
            this.bookingEmailRequest = bookingEmailRequest;

            return this;
        }

        public Builder withRetryCount(Integer retryCount) {
            this.retryCount = retryCount;

            return this;
        }

        public KinesisRecord build() {
            return new KinesisRecord(this);
        }

    }

}
