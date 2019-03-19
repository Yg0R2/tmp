package com.yg0r2.rms.domain;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = RequestContext.Builder.class)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RequestContext {

    private final String brand;
    private final String clientId;
    private final String locale;
    private final String pointOfSale;

    private RequestContext(Builder builder) {
        brand = builder.brand;
        clientId = Optional.ofNullable(builder.clientId).orElse("RequestManipulatorService");
        locale = builder.locale;
        pointOfSale = builder.pointOfSale;
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

    public static class Builder {

        private String brand;
        private String clientId;
        private String locale;
        private String pointOfSale;

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

        public RequestContext build() {
            return new RequestContext(this);
        }

    }

}
