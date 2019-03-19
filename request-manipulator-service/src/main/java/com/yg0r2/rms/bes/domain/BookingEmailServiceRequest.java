package com.yg0r2.rms.bes.domain;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.rms.domain.EmailRequest;
import com.yg0r2.rms.domain.Recipient;
import com.yg0r2.rms.domain.RequestContext;

@JsonDeserialize(builder = BookingEmailServiceRequest.Builder.class)
public class BookingEmailServiceRequest implements EmailRequest {

    private final String emailType;
    private final long timeStamp;
    private final UUID requestId;
    private final RequestContext requestContext;
    private final Order order;
    private final Set<Recipient> recipients;
    private final MetaData metaData;

    private BookingEmailServiceRequest(Builder builder) {
        emailType = builder.emailType;
        timeStamp = Optional.ofNullable(builder.timeStamp).orElse(System.currentTimeMillis());
        requestId = Optional.ofNullable(builder.requestId).orElse(UUID.randomUUID());
        requestContext = builder.requestContext;
        order = builder.order;
        recipients = Set.copyOf(builder.recipients);
        metaData = builder.metaData;
    }

    public String getEmailType() {
        return emailType;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public Order getOrder() {
        return order;
    }

    public Set<Recipient> getRecipients() {
        return recipients;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public static class Builder {

        private String emailType;
        private Long timeStamp;
        private UUID requestId;
        private RequestContext requestContext;
        private Order order;
        private Set<Recipient> recipients;
        private MetaData metaData;

        public Builder withEmailType(String emailType) {
            this.emailType = emailType;

            return this;
        }

        public Builder withTimeStamp(Long timeStamp) {
            this.timeStamp = timeStamp;

            return this;
        }

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;

            return this;
        }

        public Builder withRequestContext(RequestContext requestContext) {
            this.requestContext = requestContext;

            return this;
        }

        public Builder withOrder(Order order) {
            this.order = order;

            return this;
        }

        public Builder withRecipients(Set<Recipient> recipients) {
            this.recipients = recipients;

            return this;
        }

        public Builder withMetaData(MetaData metaData) {
            this.metaData = metaData;

            return this;
        }

        public BookingEmailServiceRequest build() {
            return new BookingEmailServiceRequest(this);
        }
    }
}
