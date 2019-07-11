package com.yg0r2.kinesis.client.example.bes.domain;

import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = BookingEmailRequest.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingEmailRequest {

    private final UUID requestId;

    private BookingEmailRequest(Builder builder) {
        requestId = builder.requestId;
    }

    public UUID getRequestId() {
        return requestId;
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

    public static Builder builder(BookingEmailRequest bookingEmailRequest) {
        return new Builder(bookingEmailRequest);
    }

    public static class Builder {

        private UUID requestId;

        private Builder() {
        }

        private Builder(BookingEmailRequest bookingEmailRequest) {
            requestId = bookingEmailRequest.requestId;
        }

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;

            return this;
        }

        public BookingEmailRequest build() {
            return new BookingEmailRequest(this);
        }

    }

}
