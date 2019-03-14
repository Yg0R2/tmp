package com.yg0r2.ces.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotels.platform.config.domain.Context;
import com.yg0r2.ces.domain.ConfirmationEmailRequest;
import com.yg0r2.common.configresolver.service.ConfigurationResolver;
import com.yg0r2.common.configresolver.service.ContextTransformer;
import com.yg0r2.common.domain.RequestContext;
import com.yg0r2.common.service.RecipientsFactory;
import com.yg0r2.common.service.ResourcesUtils;

@Service
public class ConfirmationEmailRequestFactory {

    @Autowired
    private ConfigurationResolver configurationResolver;
    @Autowired
    private ContextTransformer contextTransformer;
    @Autowired
    private RecipientsFactory recipientsFactory;
    @Autowired
    private ResourcesUtils<Map<String, String>> cesResourceUtils;

    public ConfirmationEmailRequest create(RequestContext requestContext) {
        Context context = createContext(requestContext);

        return new ConfirmationEmailRequest.Builder()
            .withClientId(getClientId(context))
            .withConversationId(requestContext.getRequestId().toString())
            .withCustomerKey(getCustomerKey(context))
            .withRecipients(recipientsFactory.create())
            .withRequestAttributes(getRequestAttributes())
            .withRequestId(requestContext.getRequestId())
            .withRequestContext(requestContext)
            .withSubscriberAttributes(getSubscriberAttributes(requestContext))
            .build();
    }

    private Context createContext(RequestContext requestContext) {
        return contextTransformer.transform(requestContext);
    }

    private int getClientId(Context context) {
        return configurationResolver.intValue("EXACT_TARGET_NEW_INFRASTRUCTURE_CLIENT_ID", context);
    }

    private String getCustomerKey(Context context) {
        return configurationResolver.get("EXACT_TARGET_NEW_INFRASTRUCTURE_CUSTOMER_KEY", context);
    }

    private Map<String, String> getRequestAttributes() {
        return cesResourceUtils.read("classpath*:requestAttributes.json").get(0);
    }

    private Map<String, String> getSubscriberAttributes(RequestContext requestContext) {
        return Map.ofEntries(
            Map.entry("ReplyToAddress", "reply@hotels.com"),
            Map.entry("FromAddress", "confirmation@mail.hotels.com"),
            Map.entry("POSListID", requestContext.getPointOfSale() + requestContext.getLocale())
        );
    }

}
