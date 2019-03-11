package com.yg0r2.bes.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.bes.domain.Recipient;

@Component
public class RecipientsFactory {

    private static final Random RND = new Random();

    @Autowired
    private List<Recipient> recipients;

    public List<Recipient> create() {
        return Stream.of(recipients)
            .map(List::size)
            .map(RND::nextInt)
            .map(recipients::get)
            .collect(Collectors.toList());
    }

}
