package com.yg0r2.eress.bes.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = MetaData.Builder.class)
class MetaData {

    private final String emailOrigin;

    private MetaData(Builder builder) {
        emailOrigin = builder.emailOrigin;
    }

    public String getEmailOrigin() {
        return emailOrigin;
    }

    public static class Builder {

        private String emailOrigin;

        public Builder withEmailOrigin(String emailOrigin) {
            this.emailOrigin = emailOrigin;

            return this;
        }

        public MetaData build() {
            return new MetaData(this);
        }

    }

}
