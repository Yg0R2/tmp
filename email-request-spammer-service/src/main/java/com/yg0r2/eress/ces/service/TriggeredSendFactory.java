package com.yg0r2.eress.ces.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exacttarget.wsdl.partnerapi.v2.Attribute;
import com.exacttarget.wsdl.partnerapi.v2.ClientID;
import com.exacttarget.wsdl.partnerapi.v2.Subscriber;
import com.exacttarget.wsdl.partnerapi.v2.TriggeredSend;
import com.exacttarget.wsdl.partnerapi.v2.TriggeredSendDefinition;
import com.hotels.platform.config.domain.Context;
import com.yg0r2.eress.ces.domain.ConfirmationEmailServiceRequest;
import com.yg0r2.eress.configresolver.service.ConfigurationResolver;
import com.yg0r2.eress.configresolver.service.ContextTransformer;
import com.yg0r2.eress.domain.Recipient;
import com.yg0r2.eress.domain.RequestContext;

@Component
class TriggeredSendFactory {

    @Autowired
    private ConfigurationResolver configurationResolver;
    @Autowired
    private ContextTransformer contextTransformer;
    @Autowired
    private XMLGregorianCalendarProvider xmlGregorianCalendarProvider;

    public TriggeredSend create(ConfirmationEmailServiceRequest request) {
        Context context = createContext(request.getRequestContext());

        TriggeredSend triggeredSend = new TriggeredSend();

        triggeredSend.setClient(createClientId(context));
        triggeredSend.setCreatedDate(xmlGregorianCalendarProvider.newXMLGregorianCalendar());
        triggeredSend.setTriggeredSendDefinition(createTriggeredSendDefinition(context));
        triggeredSend.getAttributes().addAll(createRequestAttributes(request));
        triggeredSend.getSubscribers().addAll(createSubscriberList(request));

        return triggeredSend;
    }

    private Context createContext(RequestContext requestContext) {
        return contextTransformer.transform(requestContext);
    }

    private ClientID createClientId(Context context) {
        ClientID clientID = new ClientID();

        clientID.setClientID(configurationResolver.intValue("EXACT_TARGET_NEW_INFRASTRUCTURE_CLIENT_ID", context));

        return clientID;
    }

    private TriggeredSendDefinition createTriggeredSendDefinition(Context context) {
        final TriggeredSendDefinition triggeredSendDefinition = new TriggeredSendDefinition();

        triggeredSendDefinition.setCustomerKey(configurationResolver.get("EXACT_TARGET_NEW_INFRASTRUCTURE_CUSTOMER_KEY", context));

        return triggeredSendDefinition;
    }

    private List<Attribute> createRequestAttributes(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        return confirmationEmailServiceRequest.getRequestAttributes().entrySet().stream()
            .map(this::createAttribute)
            .collect(Collectors.toList());
    }

    private Set<Subscriber> createSubscriberList(ConfirmationEmailServiceRequest confirmationEmailServiceRequest) {
        List<Attribute> attributes = createSubscriberAttributes(confirmationEmailServiceRequest.getSubscriberAttributes());
        String bookingPosListId = createBookingPosListId(confirmationEmailServiceRequest.getRequestContext());

        return confirmationEmailServiceRequest.getRecipients().stream()
            .map(Recipient::getEmailAddress)
            .map(emailAddress -> createSubscriber(attributes, emailAddress, bookingPosListId))
            .collect(Collectors.toSet());
    }

    private List<Attribute> createSubscriberAttributes(Map<String, String> subscriberAttributes) {
        return subscriberAttributes.entrySet().stream()
            .map(this::createAttribute)
            .collect(Collectors.toList());
    }

    private Attribute createAttribute(Map.Entry<String, String> mapEntry) {
        Attribute attribute = new Attribute();

        attribute.setName(mapEntry.getKey());
        attribute.setValue(mapEntry.getValue());

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
        subscriber.getAttributes().add(createAttribute(Map.entry("POSListID", bookingPosListId)));

        return subscriber;
    }
}
