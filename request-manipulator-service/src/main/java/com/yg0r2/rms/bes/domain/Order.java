package com.yg0r2.rms.bes.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Order.Builder.class)
class Order {

    private final String customerLastName;
    private final long orderNumber;

    private Order(Builder builder) {
        customerLastName = builder.customerLastName;
        orderNumber = builder.orderNumber;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public static class Builder {

        private String customerLastName;
        private Long orderNumber;

        public Builder withCustomerLastName(String customerLastName) {
            this.customerLastName = customerLastName;

            return this;
        }

        public Builder withOrderNumber(Long orderNumber) {
            this.orderNumber = orderNumber;

            return this;
        }

        public Order build() {
            return new Order(this);
        }


    }

}
