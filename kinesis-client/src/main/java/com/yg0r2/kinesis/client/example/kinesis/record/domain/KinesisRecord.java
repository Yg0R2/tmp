package com.yg0r2.kinesis.client.example.kinesis.record.domain;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = KinesisRecord.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class KinesisRecord {

    private final UUID requestId;
    private final int retryCount;

    private KinesisRecord(Builder builder) {
        requestId = builder.requestId;
        retryCount = Optional.ofNullable(builder.retryCount).orElse(0);
    }

    public UUID getRequestId() {
        return requestId;
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

    public static class Builder {

        private UUID requestId;
        private Integer retryCount;

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;

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
