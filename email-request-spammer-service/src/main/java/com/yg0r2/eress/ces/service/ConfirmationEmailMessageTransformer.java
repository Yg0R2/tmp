package com.yg0r2.eress.ces.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotels.event.eventmetadata.EventMetadata;
import com.hotels.messaging.MessageFactory;
import com.hotels.services.common.messaging.v20100812.Error;
import com.hotels.services.eventservice.confemail.content.ConfirmationEmailMessage;
import com.yg0r2.eress.ces.domain.ConfirmationEmailServiceRequest;

@Component
public class ConfirmationEmailMessageTransformer {

    @Autowired
    private CreateRequestTransformer createRequestTransformer;
    @Autowired
    private MessageFactory<ConfirmationEmailMessage, Error> messageFactory;

    public ConfirmationEmailMessage transform(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        ConfirmationEmailMessage confirmationEmailMessage = messageFactory.createMessage();

        confirmationEmailMessage.setEventMetadata(createEventMetadata(confirmationEmailMessage.getMessageEnvelope().getId()));
        confirmationEmailMessage.setCreateRequest(createRequestTransformer.transform(confirmationEmailServiceRequest));

        return confirmationEmailMessage;
    }

    private EventMetadata createEventMetadata(UUID eventId) {
        EventMetadata eventMetadata = new EventMetadata();

        eventMetadata.setEventid(eventId.toString());

        return eventMetadata;
    }

}
