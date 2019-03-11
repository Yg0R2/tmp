package com.yg0r2.bes.domain;

import java.util.List;

public class BookingEmailRequest {

    private final String emailType;
    private final MetaData metaData;
    private final Order order;
    private final List<Recipient> recipients;

    private BookingEmailRequest(Builder builder) {
        emailType = builder.emailType;
        metaData = builder.metaData;
        order = builder.order;
        recipients = List.copyOf(builder.recipients);
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

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public static class Builder {

        private String emailType;
        private MetaData metaData;
        private Order order;
        private List<Recipient> recipients;

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

        public Builder withRecipients(List<Recipient> recipients) {
            this.recipients = recipients;

            return this;
        }

        public BookingEmailRequest build() {
            return new BookingEmailRequest(this);
        }
    }
}
