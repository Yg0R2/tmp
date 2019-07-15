package com.yg0r2.kinesis.client.example.bes.domain;

import java.util.UUID;

public class ServiceCallContext {

    private final UUID requestId;

    private ServiceCallContext(Builder builder) {
        requestId = builder.requestId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private UUID requestId;

        private Builder() {
        }

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;

            return this;
        }

        public ServiceCallContext build() {
            return new ServiceCallContext(this);
        }

    }

}
