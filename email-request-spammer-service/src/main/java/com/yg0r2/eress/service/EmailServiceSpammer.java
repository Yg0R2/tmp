package com.yg0r2.eress.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.yg0r2.eress.domain.EmailResponse;

public class EmailServiceSpammer {

    private final EmailServiceCallable<?, ?> emailServiceCallable;

    public EmailServiceSpammer(EmailServiceCallable<?, ?> emailServiceCallable) {
        this.emailServiceCallable = emailServiceCallable;
    }

    public List<EmailResponse> spam(int count) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        List<EmailResponse> responses;
        try {
            List<Future<EmailResponse>> futureResponses = IntStream.range(0, count)
                .mapToObj(i -> executorService.submit(emailServiceCallable))
                .collect(Collectors.toList());

            responses = futureResponses.stream()
                .map(this::getResponse)
                .parallel()
                .collect(Collectors.toList());
        }
        finally {
            if (!executorService.isShutdown()) {
                executorService.shutdown();
            }
        }

        return responses;
    }

    private EmailResponse getResponse(Future<EmailResponse> responseFuture) {
        try {
            return responseFuture.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
