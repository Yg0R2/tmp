package com.yg0r2.rms.ces.service;

import org.springframework.stereotype.Component;

import com.exacttarget.wsdl.partnerapi.v2.CreateOptions;
import com.exacttarget.wsdl.partnerapi.v2.Priority;
import com.exacttarget.wsdl.partnerapi.v2.RequestType;

@Component
class CreateOptionsFactory {

    public CreateOptions create(String conversationId) {
        CreateOptions createOptions = new CreateOptions();

        createOptions.setRequestType(RequestType.ASYNCHRONOUS);
        createOptions.setQueuePriority(Priority.HIGH);
        createOptions.setConversationID(conversationId);

        return createOptions;
    }
}
