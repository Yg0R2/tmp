package com.yg0r2.bes.domain;

public class Recipient {

    private final String emailAddress;

    public Recipient(Builder builder) {
        emailAddress = builder.emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public static class Builder {

        private String emailAddress;

        public Builder withEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;

            return this;
        }

        public Recipient build() {
            return new Recipient(this);
        }

    }

}
