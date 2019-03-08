package com.yg0r2.bes.service.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.yg0r2.bes.domain.Recipient;

@Configuration
public class EmailSenderConfiguration {

    @Bean
    public RestTemplate emailSenderRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public List<Recipient> recipients() {
        return List.of(
            createRecipient("v-akajzer@hotels.com"),
            createRecipient("v-gkormendi@hotels.com"),
            createRecipient("v-gekovacs@hotels.com"),
            createRecipient("v-ligaz@hotels.com"),
            createRecipient("v-tikovacs@hotels.com"),
            createRecipient("v-mhodovan@hotels.com"),
            createRecipient("v-mteleki@hotels.com"),
            createRecipient("v-babodnar@hotels.com"),
            createRecipient("v-pkurti@hotels.com")
        );
    }

    private Recipient createRecipient(String emailAddress) {
        return new Recipient.Builder()
            .withEmailAddress(emailAddress)
            .build();
    }

}
