package com.yg0r2.bes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.bes.domain.BookingEmailRequest;
import com.yg0r2.common.service.RecipientsFactory;

@Component
public class BookingEmailRequestFactory {

    @Autowired
    private MetaDataFactory metaDataFactory;
    @Autowired
    private OrderFactory orderFactory;
    @Autowired
    private RecipientsFactory recipientsFactory;

    public BookingEmailRequest create(Long orderNumber, String lastName) {
        return new BookingEmailRequest.Builder()
            .withEmailType("orderConfirmationEmail")
            .withMetaData(metaDataFactory.create())
            .withOrder(orderFactory.create(orderNumber, lastName))
            .withRecipients(recipientsFactory.create())
            .build();
    }

}
