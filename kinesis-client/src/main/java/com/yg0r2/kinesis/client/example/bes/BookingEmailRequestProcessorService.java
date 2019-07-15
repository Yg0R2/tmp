package com.yg0r2.kinesis.client.example.bes;

import com.yg0r2.kinesis.client.example.bes.domain.BookingEmailRequest;

public interface BookingEmailRequestProcessorService {

    void processRequest(BookingEmailRequest bookingEmailRequest);

}
