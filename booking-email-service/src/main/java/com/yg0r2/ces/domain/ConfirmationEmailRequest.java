package com.yg0r2.ces.domain;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.yg0r2.common.domain.Recipient;
import com.yg0r2.common.domain.RequestContext;

public class ConfirmationEmailRequest {

    private final Integer clientId;
    private final String conversationId;
    private final String customerKey;
    private final Set<Recipient> recipients;
    private final RequestContext requestContext;
    private final Map<String, String> requestAttributes;
    private final Map<String, String> subscriberAttributes;
    private final UUID requestId;

    private ConfirmationEmailRequest(Builder builder) {
        this.clientId = builder.clientId;
        this.conversationId = builder.conversationId;
        this.customerKey = builder.customerKey;
        this.recipients = builder.recipients;
        this.requestContext = builder.requestContext;
        this.requestAttributes = builder.requestAttributes;
        this.subscriberAttributes = builder.subscriberAttributes;
        this.requestId = builder.requestId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getCustomerKey() {
        return customerKey;
    }

    public Set<Recipient> getRecipients() {
        return recipients;
    }

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public Map<String, String> getRequestAttributes() {
        return requestAttributes;
    }

    public Map<String, String> getSubscriberAttributes() {
        return subscriberAttributes;
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

    public static class Builder {

        private Integer clientId;
        private String conversationId;
        private String customerKey;
        private Set<Recipient> recipients;
        private RequestContext requestContext;
        private Map<String, String> requestAttributes;
        private Map<String, String> subscriberAttributes;
        private UUID requestId;

        public Builder withClientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withConversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public Builder withCustomerKey(String customerKey) {
            this.customerKey = customerKey;
            return this;
        }

        public Builder withRecipients(Set<Recipient> recipients) {
            this.recipients = recipients;
            return this;
        }

        public Builder withRequestContext(RequestContext requestContext) {
            this.requestContext = requestContext;
            return this;
        }

        public Builder withRequestAttributes(Map<String, String> requestAttributes) {
            this.requestAttributes = requestAttributes;
            return this;
        }

        public Builder withSubscriberAttributes(Map<String, String> subscriberAttributes) {
            this.subscriberAttributes = subscriberAttributes;
            return this;
        }

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;
            return this;
        }

        public ConfirmationEmailRequest build() {
            return new ConfirmationEmailRequest(this);
        }

    }

}
