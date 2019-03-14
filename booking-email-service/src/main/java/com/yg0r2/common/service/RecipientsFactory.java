package com.yg0r2.common.service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yg0r2.common.domain.Recipient;

@Component
public class RecipientsFactory {

    private static final Random RND = new Random();

    @Autowired
    private List<Recipient> recipients;

    public Set<Recipient> create() {
        return Stream.of(recipients)
            .map(List::size)
            .map(RND::nextInt)
            .map(recipients::get)
            .collect(Collectors.toSet());
    }

}
