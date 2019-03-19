package com.yg0r2.rms.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.rms.bes.domain.BookingEmailServiceRequest;
import com.yg0r2.rms.bes.service.BookingEmailService;
import com.yg0r2.rms.domain.EmailResponse;
import com.yg0r2.rms.service.EmailService;
import com.yg0r2.rms.service.EmailServiceCallable;
import com.yg0r2.rms.service.EmailServiceRequestFactory;
import com.yg0r2.rms.service.EmailServiceSpammer;
import com.yg0r2.rms.service.ResourcesUtils;

@Configuration
public class BookingEmailRequestEdgeServiceConfiguration {

    private static final String BOOKING_EMAIL_REQUEST_EDGE_SERVICE_REQUEST_JSON = "classpath:BERES_request.json";

    @Value("${beres.url}")
    private String serviceUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public EmailServiceSpammer beresSpammer() {
        return new EmailServiceSpammer(beresCallable());
    }

    private EmailServiceCallable<BookingEmailServiceRequest, EmailResponse> beresCallable() {
        return new EmailServiceCallable<>(beresRequestFactory(), beresEmailService());
    }

    private EmailServiceRequestFactory<BookingEmailServiceRequest> beresRequestFactory() {
        return new EmailServiceRequestFactory<>(beresResourcesUtils(), BOOKING_EMAIL_REQUEST_EDGE_SERVICE_REQUEST_JSON);
    }

    private ResourcesUtils<BookingEmailServiceRequest> beresResourcesUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

    private EmailService<BookingEmailServiceRequest, EmailResponse> beresEmailService() {
        return new BookingEmailService(serviceUrl, beresRestTemplate());
    }

    private RestTemplate beresRestTemplate() {
        CloseableHttpClient httpClient = HttpClients
            .custom()
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }

}
