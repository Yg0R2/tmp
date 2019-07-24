package com.yg0r2.tmp.resttemplate.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InfiniteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfiniteService.class);

    public String getData() {
        LOGGER.info("infinite controller called, start waiting xD");

        if (true) {
            while (true) ;
        }

        return "infinite";
    }

}
