package com.yg0r2.circuitbreaker.thermos;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.DefaultThermosEngine;
import com.hotels.thermos.ThermosEngine;
import com.hotels.thermos.spring.circuitbreaker.CircuitBreakerMethodConfigs;
import com.hotels.thermos.spring.config.ThermosSpringConfig;
import com.hotels.thermos.spring.domain.CircuitBreakerConfigurationWrapper;

@Configuration
@ConfigurationProperties(prefix = "thermos")
@ComponentScan("com.hotels.thermos.spring.*")
public class ThermosConfig {

    private Map<String, CircuitBreakerConfiguration> methodConfigs;

    @Bean
    public ThermosEngine thermosEngine() {
        return new DefaultThermosEngine();
    }

    @Bean
    public ThermosSpringConfig thermosSpring() {
        return new ThermosSpringConfig(true, createCircuitBreakerConfigs());
    }

    public Map<String, CircuitBreakerConfiguration> getMethodConfigs() {
        return methodConfigs;
    }

    public void setMethodConfigs(Map<String, CircuitBreakerConfiguration> methodConfigs) {
        this.methodConfigs = methodConfigs;
    }

    private CircuitBreakerMethodConfigs createCircuitBreakerConfigs() {
        CircuitBreakerMethodConfigs configs = new CircuitBreakerMethodConfigs();

        methodConfigs.forEach((key, value) -> configs.addWrappedConfig(key, createCircuitBreakerConfigurationWrapper(value)));

        return configs;
    }

    private CircuitBreakerConfigurationWrapper createCircuitBreakerConfigurationWrapper(CircuitBreakerConfiguration circuitBreakerConfiguration) {
        return CircuitBreakerConfigurationWrapper.CircuitBreakerConfigurationWrapperBuilder.builder()
            .commandName(circuitBreakerConfiguration.getCommandName().orElse(null))
            .groupName(circuitBreakerConfiguration.getGroupName().orElse(null))
            .circuitBreakerConfiguration(circuitBreakerConfiguration)
            .build();
    }

    @Configuration
    @ConfigurationProperties(prefix = "thermos.method-configs")
    protected static class CircuitBreakerConfiguration extends com.hotels.thermos.CircuitBreakerConfiguration {

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

    }

}
