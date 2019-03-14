package com.yg0r2.common.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.common.domain.Recipient;
import com.yg0r2.common.service.ResourcesUtils;

@Configuration
public class RecipientsConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public List<Recipient> recipients() {
        ResourcesUtils<List<String>> resourcesUtils = new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);

        return resourcesUtils.read("classpath*:recipients.json").stream()
            .map(list -> list.get(0))
            .map(this::createRecipient)
            .collect(Collectors.toList());
    }

    private Recipient createRecipient(String emailAddress) {
        return new Recipient.Builder()
            .withEmailAddress(emailAddress)
            .build();
    }

}
