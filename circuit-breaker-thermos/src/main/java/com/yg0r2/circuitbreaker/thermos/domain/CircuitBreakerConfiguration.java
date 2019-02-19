package com.yg0r2.circuitbreaker.thermos.domain;

import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

public class CircuitBreakerConfiguration  extends com.hotels.thermos.CircuitBreakerConfiguration {

    private Optional<String> commandName;
    private Optional<String> groupName;

    public Optional<String> getCommandName() {
        return commandName;
    }

    public void setCommandName(Optional<String> commandName) {
        this.commandName = commandName;
    }

    public Optional<String> getGroupName() {
        return groupName;
    }

    public void setGroupName(Optional<String> groupName) {
        this.groupName = groupName;
    }

    @Configuration
    @ConfigurationProperties(prefix = "thermos.method-configs")
    public static class CircuitBreakerMethodConfiguration extends CircuitBreakerConfiguration {
    }

    @Configuration
    @ConfigurationProperties(prefix = "thermos.service-configs")
    public static class CircuitBreakerServiceConfiguration extends CircuitBreakerConfiguration {
    }

}