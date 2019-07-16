package com.yg0r2.kinesis.client.example.messaging.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;

@JsonDeserialize(builder = MessageRecord.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MessageRecord {

    private final BookingEmailRequest bookingEmailRequest;
    private final LocalDateTime createDateTime;
    private final LocalDateTime nextRetryDateTime;
    private final int retryCount;

    private MessageRecord(Builder builder) {
        bookingEmailRequest = builder.bookingEmailRequest;
        createDateTime = Optional.ofNullable(builder.createDateTime).orElse(LocalDateTime.now());
        nextRetryDateTime = builder.nextRetryDateTime;
        retryCount = Optional.ofNullable(builder.retryCount).orElse(0);
    }

    public BookingEmailRequest getBookingEmailRequest() {
        return bookingEmailRequest;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getNextRetryDateTime() {
        return nextRetryDateTime;
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

    public static Builder builder(MessageRecord messageRecord) {
        return new Builder(messageRecord);
    }

    public static class Builder {

        private BookingEmailRequest bookingEmailRequest;
        private LocalDateTime createDateTime;
        private LocalDateTime nextRetryDateTime;
        private Integer retryCount;

        private Builder() {
        }

        private Builder(MessageRecord messageRecord) {
            bookingEmailRequest = messageRecord.getBookingEmailRequest();
            createDateTime = messageRecord.createDateTime;
            nextRetryDateTime = messageRecord.nextRetryDateTime;
            retryCount = messageRecord.getRetryCount();
        }

        public Builder withBookingEmailRequest(BookingEmailRequest bookingEmailRequest) {
            this.bookingEmailRequest = bookingEmailRequest;

            return this;
        }

        public Builder withCreateDateTime(LocalDateTime createDateTime) {
            this.createDateTime = createDateTime;

            return this;
        }

        public Builder withNextRetryDateTime(LocalDateTime nextRetryDateTime) {
            this.nextRetryDateTime = nextRetryDateTime;

            return this;
        }

        public Builder withRetryCount(Integer retryCount) {
            this.retryCount = retryCount;

            return this;
        }

        public MessageRecord build() {
            return new MessageRecord(this);
        }

    }

}
