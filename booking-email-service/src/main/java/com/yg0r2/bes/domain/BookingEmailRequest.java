package com.yg0r2.bes.domain;

import java.util.Set;

import com.yg0r2.common.domain.Recipient;

public class BookingEmailRequest {

    private final String emailType;
    private final MetaData metaData;
    private final Order order;
    private final Set<Recipient> recipients;

    private BookingEmailRequest(Builder builder) {
        emailType = builder.emailType;
        metaData = builder.metaData;
        order = builder.order;
        recipients = Set.copyOf(builder.recipients);
    }

    public String getEmailType() {
        return emailType;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public Order getOrder() {
        return order;
    }

    public Set<Recipient> getRecipients() {
        return recipients;
    }

    public static class Builder {

        private String emailType;
        private MetaData metaData;
        private Order order;
        private Set<Recipient> recipients;

        public Builder withEmailType(String emailType) {
            this.emailType = emailType;

            return this;
        }

        public Builder withMetaData(MetaData metaData) {
            this.metaData = metaData;

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

        public BookingEmailRequest build() {
            return new BookingEmailRequest(this);
        }
    }
}
