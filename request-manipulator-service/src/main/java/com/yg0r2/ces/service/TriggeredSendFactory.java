package com.yg0r2.ces.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.exacttarget.wsdl.partnerapi.v2.Attribute;
import com.exacttarget.wsdl.partnerapi.v2.ClientID;
import com.exacttarget.wsdl.partnerapi.v2.Subscriber;
import com.exacttarget.wsdl.partnerapi.v2.TriggeredSend;
import com.exacttarget.wsdl.partnerapi.v2.TriggeredSendDefinition;
import com.yg0r2.ces.domain.ConfirmationEmailRequest;
import com.yg0r2.common.domain.Recipient;
import com.yg0r2.common.domain.RequestContext;

@Component
class TriggeredSendFactory {

    @Autowired
    private XMLGregorianCalendarProvider xmlGregorianCalendarProvider;

    public TriggeredSend create(ConfirmationEmailRequest request) {
        TriggeredSend triggeredSend = new TriggeredSend();

        triggeredSend.setClient(createClientId(request.getClientId()));
        triggeredSend.setCreatedDate(xmlGregorianCalendarProvider.newXMLGregorianCalendar());
        triggeredSend.setTriggeredSendDefinition(createTriggeredSendDefinition(request.getCustomerKey()));
        triggeredSend.getAttributes().addAll(createTriggeredSendAttributes(request));
        triggeredSend.getSubscribers().addAll(createSubscriberList(request));

        return triggeredSend;
    }

    private ClientID createClientId(Integer clientId) {
        ClientID clientID = new ClientID();

        clientID.setClientID(clientId);

        return clientID;
    }

    private TriggeredSendDefinition createTriggeredSendDefinition(String customerKey) {
        final TriggeredSendDefinition triggeredSendDefinition = new TriggeredSendDefinition();

        triggeredSendDefinition.setCustomerKey(customerKey);

        return triggeredSendDefinition;
    }

    private Set<Subscriber> createSubscriberList(ConfirmationEmailRequest confirmationEmailRequest) {
        Set<Recipient> recipients = confirmationEmailRequest.getRecipients();
        Assert.notEmpty(recipients, "confirmationEmailRequest.getRecipients() must not be null!");

        List<Attribute> attributes = getSubscriberAttributes(confirmationEmailRequest.getSubscriberAttributes());
        String bookingPosListId = createBookingPosListId(confirmationEmailRequest.getRequestContext());

        return recipients.stream()
            .map(Recipient::getEmailAddress)
            .map(emailAddress -> createSubscriber(attributes, emailAddress, bookingPosListId))
            .collect(Collectors.toSet());
    }

    private List<Attribute> getSubscriberAttributes(Map<String, String> subscriberAttributes) {
        return subscriberAttributes.entrySet().stream().map(entry -> createAttribute(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private List<Attribute> createTriggeredSendAttributes(ConfirmationEmailRequest confirmationEmailRequest) {
        return confirmationEmailRequest.getRequestAttributes().entrySet().stream().map(entry -> createAttribute(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    private Attribute createAttribute(String name, String value) {
        Attribute attribute = new Attribute();

        attribute.setName(name);
        attribute.setValue(value);

        return attribute;
    }

    private String createBookingPosListId(RequestContext requestContext) {
        return Optional.of(requestContext)
            .map(context -> context.getPointOfSale() + context.getLocale())
            .orElse("");
    }

    private Subscriber createSubscriber(List<Attribute> attributes, String emailAddress, String bookingPosListId) {
        Subscriber subscriber = new Subscriber();

        subscriber.setSubscriberKey(emailAddress + bookingPosListId);
        subscriber.setEmailAddress(emailAddress);
        subscriber.getAttributes().addAll(attributes);

        return subscriber;
    }
}
