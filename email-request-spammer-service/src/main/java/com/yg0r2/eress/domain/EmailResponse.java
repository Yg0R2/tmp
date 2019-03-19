package com.yg0r2.eress.domain;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = EmailResponse.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailResponse {

    private final UUID requestId;

    private EmailResponse(Builder builder) {
        requestId = builder.requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Builder {

        private UUID requestId;

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;

            return this;
        }

        public EmailResponse build() {
            return new EmailResponse(this);
        }
    }
}
