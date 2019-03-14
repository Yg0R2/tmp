package com.yg0r2.ces.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exacttarget.wsdl.partnerapi.v2.CreateRequest;
import com.yg0r2.ces.domain.ConfirmationEmailRequest;

@Component
class CreateRequestTransformer {

    @Autowired
    private CreateOptionsFactory createOptionsFactory;
    @Autowired
    private TriggeredSendFactory triggeredSendFactory;

    public CreateRequest transform(ConfirmationEmailRequest confirmationEmailRequest) {
        CreateRequest createRequest = new CreateRequest();

        createRequest.setOptions(createOptionsFactory.create(confirmationEmailRequest.getConversationId()));
        createRequest.getObjects().add(triggeredSendFactory.create(confirmationEmailRequest));

        return createRequest;
    }

}
