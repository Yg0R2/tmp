package com.yg0r2.rms.bes.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.rms.bes.domain.BookingEmailServiceRequest;
import com.yg0r2.rms.bes.service.BookingEmailService;
import com.yg0r2.rms.domain.EmailResponse;
import com.yg0r2.rms.service.EmailServiceCallable;
import com.yg0r2.rms.service.EmailServiceSpammer;
import com.yg0r2.rms.service.EmailService;
import com.yg0r2.rms.service.EmailServiceRequestFactory;
import com.yg0r2.rms.service.ResourcesUtils;

@Configuration
public class BookingEmailServiceConfiguration {

    private static final String BOOKING_EMAIL_SERVICE_REQUEST_JSON = "classpath:BES_request.json";

    @Value("${bes.url}")
    private String serviceUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public EmailServiceSpammer besSpammer() {
        return new EmailServiceSpammer(besCallable());
    }

    private EmailServiceCallable<BookingEmailServiceRequest, EmailResponse> besCallable() {
        return new EmailServiceCallable<>(besRequestFactory(), besEmailService());
    }

    private EmailServiceRequestFactory<BookingEmailServiceRequest> besRequestFactory() {
        return new EmailServiceRequestFactory<>(besResourcesUtils(), BOOKING_EMAIL_SERVICE_REQUEST_JSON);
    }

    private ResourcesUtils<BookingEmailServiceRequest> besResourcesUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

    private EmailService<BookingEmailServiceRequest, EmailResponse> besEmailService() {
        return new BookingEmailService(serviceUrl, besRestTemplate());
    }

    private RestTemplate besRestTemplate() {
        return new RestTemplate();
    }

}
