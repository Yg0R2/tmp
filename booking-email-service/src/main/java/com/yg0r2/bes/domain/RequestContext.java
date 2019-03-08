package com.yg0r2.bes.domain;

import java.util.UUID;

public class RequestContext {

    private final String brand;
    private final String clientId;
    private final String locale;
    private final String pointOfSale;
    private final UUID requestId;

    private RequestContext(Builder builder) {
        brand = builder.brand;
        clientId = builder.clientId;
        locale = builder.locale;
        pointOfSale = builder.pointOfSale;
        requestId = builder.requestId;
    }

    public String getBrand() {
        return brand;
    }

    public String getClientId() {
        return clientId;
    }

    public String getLocale() {
        return locale;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public static class Builder {

        private String brand;
        private String clientId;
        private String locale;
        private String pointOfSale;
        private UUID requestId;

        public Builder withBrand(String brand) {
            this.brand = brand;

            return this;
        }

        public Builder withClientId(String clientId) {
            this.clientId = clientId;

            return this;
        }

        public Builder withLocale(String locale) {
            this.locale = locale;

            return this;
        }

        public Builder withPointOfSale(String pointOfSale) {
            this.pointOfSale = pointOfSale;

            return this;
        }

        public Builder withRequestId(UUID requestId) {
            this.requestId = requestId;

            return this;
        }

        public RequestContext build() {
            return new RequestContext(this);
        }
    }

}
