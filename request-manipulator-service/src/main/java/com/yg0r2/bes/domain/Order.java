package com.yg0r2.bes.domain;

import java.util.Optional;

public class Order {

    private final String customerLastName;
    private final long orderNumber;

    private Order(Builder builder) {
        customerLastName = Optional.ofNullable(builder.customerLastName).orElse("Odinson-xby");
        orderNumber = Optional.ofNullable(builder.orderNumber).orElse(8141542976707L);
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
