package com.yg0r2.tmp.resttemplate.thermos;

import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

public class CircuitBreakerConfiguration  extends com.hotels.thermos.CircuitBreakerConfiguration {

    private String commandName;
    private String groupName;

    public Optional<String> getCommandName() {
        return Optional.ofNullable(commandName);
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public Optional<String> getGroupName() {
        return Optional.ofNullable(groupName);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Configuration
    @ConfigurationProperties(prefix = "thermos.method-configs")
    public static class CircuitBreakerMethodConfiguration extends CircuitBreakerConfiguration {
    }

    /*@Configuration
    @ConfigurationProperties(prefix = "thermos.service-configs")
    public static class CircuitBreakerServiceConfiguration extends CircuitBreakerConfiguration {
    }*/

}