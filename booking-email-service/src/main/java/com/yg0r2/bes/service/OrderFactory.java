package com.yg0r2.bes.service;

import org.springframework.stereotype.Component;

import com.yg0r2.bes.domain.Order;

@Component
public class OrderFactory {

    public Order create(Long orderNumber, String lastName) {
        return new Order.Builder()
            .withCustomerLastName(lastName)
            .withOrderNumber(orderNumber)
            .build();
    }

}
