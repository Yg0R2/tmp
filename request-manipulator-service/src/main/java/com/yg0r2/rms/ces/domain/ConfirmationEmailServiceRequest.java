package com.yg0r2.rms.ces.domain;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.rms.domain.EmailRequest;
import com.yg0r2.rms.domain.Recipient;
import com.yg0r2.rms.domain.RequestContext;

@JsonDeserialize(builder = ConfirmationEmailServiceRequest.Builder.class)
public class ConfirmationEmailServiceRequest implements EmailRequest {

    private final UUID requestId;
    private final String conversationId;
    private final RequestContext requestContext;
    private final Set<Recipient> recipients;
    private final Map<String, String> subscriberAttributes;
    private final Map<String, String> requestAttributes;

    private ConfirmationEmailServiceRequest(Builder builder) {
        UUID uuid = UUID.randomUUID();

        this.requestId = Optional.ofNullable(builder.requestId).orElse(uuid);
        this.conversationId = Optional.ofNullable(builder.conversationId).orElse(uuid.toString());
        this.requestContext = builder.requestContext;
        this.recipients = builder.recipients;
        this.subscriberAttributes = builder.subscriberAttributes;
        this.requestAttributes = builder.requestAttributes;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public Set<Recipient> getRecipients() {
        return recipients;
    }

    public Map<String, String> getSubscriberAttributes() {
        return subscriberAttributes;
    }

    public Map<String, String> getRequestAttributes() {
        return requestAttributes;
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

    public static class Builder {

        private UUID requestId;
        private String conversationId;
        private RequestContext requestContext;
        private Set<Recipient> recipients;
        private Map<String, String> subscriberAttributes;
        private Map<String, String> requestAttributes;

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder withConversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public Builder withRequestContext(RequestContext requestContext) {
            this.requestContext = requestContext;
            return this;
        }

        public Builder withRecipients(Set<Recipient> recipients) {
            this.recipients = recipients;
            return this;
        }

        public Builder withSubscriberAttributes(Map<String, String> subscriberAttributes) {
            this.subscriberAttributes = subscriberAttributes;
            return this;
        }

        public Builder withRequestAttributes(Map<String, String> requestAttributes) {
            this.requestAttributes = requestAttributes;
            return this;
        }

        public ConfirmationEmailServiceRequest build() {
            return new ConfirmationEmailServiceRequest(this);
        }

    }

}
