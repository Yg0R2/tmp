package com.yg0r2.kinesis.client.example.messaging.retry.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class RetryContext {

    private final LocalDateTime requestCreateDateTime;
    private final LocalDateTime requestNextRetryDateTime;
    private final int retryCount;
    private final Throwable throwable;

    private RetryContext(Builder builder) {
        requestCreateDateTime = Optional.ofNullable(builder.requestCreateDateTime).orElse(LocalDateTime.now());
        requestNextRetryDateTime = Optional.ofNullable(builder.requestNextRetryDateTime).orElse(LocalDateTime.now());
        retryCount = Optional.ofNullable(builder.retryCount).orElse(1);
        throwable = builder.throwable;
    }

    public LocalDateTime getRequestCreateDateTime() {
        return requestCreateDateTime;
    }

    public LocalDateTime getRequestNextRetryDateTime() {
        return requestNextRetryDateTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Throwable getThrowable() {
        return throwable;
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

    public static Builder builder(RetryContext retryContext) {
        return new Builder(retryContext);
    }

    /**
     * Builder for {@link RetryContext}.
     */
    public static class Builder {

        private LocalDateTime requestCreateDateTime;
        private LocalDateTime requestNextRetryDateTime;
        private Integer retryCount;
        private Throwable throwable;

        private Builder(){
        }

        private Builder(RetryContext retryContext) {
            requestCreateDateTime = retryContext.requestCreateDateTime;
            requestNextRetryDateTime = retryContext.requestNextRetryDateTime;
            retryCount = retryContext.retryCount;
            throwable = retryContext.throwable;
        }

        public Builder withRequestCreateDateTime(LocalDateTime requestCreateDateTime) {
            this.requestCreateDateTime = requestCreateDateTime;

            return this;
        }

        public Builder withRequestNextRetryDateTime(LocalDateTime requestNextRetryDateTime) {
            this.requestNextRetryDateTime = requestNextRetryDateTime;

            return this;
        }

        public Builder withRetryCount(Integer retryCount) {
            this.retryCount = retryCount;

            return this;
        }

        public Builder withThrowable(Throwable throwable) {
            this.throwable = throwable;

            return this;
        }

        public RetryContext build() {
            return new RetryContext(this);
        }

    }

}
