package com.yg0r2.rms.service;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yg0r2.rms.domain.EmailRequest;
import com.yg0r2.rms.domain.EmailResponse;

public class EmailServiceCallable<REQUEST extends EmailRequest, POST_RESPONSE> implements Callable<EmailResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceCallable.class);

    private final EmailServiceRequestFactory<REQUEST> emailServiceRequestFactory;
    private final EmailService<REQUEST, POST_RESPONSE> emailService;

    public EmailServiceCallable(EmailServiceRequestFactory<REQUEST> emailServiceRequestFactory, EmailService<REQUEST, POST_RESPONSE> emailService) {
        this.emailServiceRequestFactory = emailServiceRequestFactory;
        this.emailService = emailService;
    }

    @Override
    public EmailResponse call() {
        LOGGER.debug(Thread.currentThread().getName());

        REQUEST emailRequest = emailServiceRequestFactory.create();

        return emailService.sendRequest(emailRequest);
    }

}
