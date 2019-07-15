package com.yg0r2.kinesis.client.example.bes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;

@Component
public class DefaultBookingEmailRequestProcessorService implements BookingEmailRequestProcessorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBookingEmailRequestProcessorService.class);

    @Override
    public void processRequest(BookingEmailRequest bookingEmailRequest) {
        LOGGER.info("Processing request: {}", bookingEmailRequest);

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
        }

        if (true) {
            throw new RuntimeException("hihi exception :)");
        }

        LOGGER.info("Processed request: {}", bookingEmailRequest);
    }

}
