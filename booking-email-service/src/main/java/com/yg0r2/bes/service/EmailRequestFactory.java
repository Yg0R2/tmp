package com.yg0r2.bes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.bes.domain.EmailRequest;

@Component
public class EmailRequestFactory {

    @Autowired
    private MetaDataFactory metaDataFactory;
    @Autowired
    private OrderFactory orderFactory;
    @Autowired
    private RecipientsFactory recipientsFactory;

    public EmailRequest create(Long orderNumber, String lastName) {
        return new EmailRequest.Builder()
            .withEmailType("orderConfirmationEmail")
            .withMetaData(metaDataFactory.create())
            .withOrder(orderFactory.create(orderNumber, lastName))
            .withRecipients(recipientsFactory.create())
            .build();
    }

}
