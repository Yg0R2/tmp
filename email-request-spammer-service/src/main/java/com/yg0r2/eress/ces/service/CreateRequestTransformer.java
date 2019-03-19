package com.yg0r2.eress.ces.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exacttarget.wsdl.partnerapi.v2.CreateRequest;
import com.yg0r2.eress.ces.domain.ConfirmationEmailServiceRequest;

@Component
class CreateRequestTransformer {

    @Autowired
    private CreateOptionsFactory createOptionsFactory;
    @Autowired
    private TriggeredSendFactory triggeredSendFactory;

    public CreateRequest transform(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        CreateRequest createRequest = new CreateRequest();

        createRequest.setOptions(createOptionsFactory.create(confirmationEmailServiceRequest.getConversationId()));
        createRequest.getObjects().add(triggeredSendFactory.create(confirmationEmailServiceRequest));

        return createRequest;
    }

}
