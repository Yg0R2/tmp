package com.yg0r2.tmp.resttemplate.thermos;

import java.util.Collections;
import java.util.Map;

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

    private Map<String, CircuitBreakerConfiguration.CircuitBreakerMethodConfiguration> methodConfigs;
    //private Map<String, CircuitBreakerConfiguration.CircuitBreakerServiceConfiguration> serviceConfigs;

    @Bean
    public ThermosEngine thermosEngine() {
        return new DefaultThermosEngine();
    }

    @Bean
    public ThermosSpringConfig thermosSpring() {
        return new ThermosSpringConfig(true, createCircuitBreakerConfigs());
    }

    public Map<String, CircuitBreakerConfiguration.CircuitBreakerMethodConfiguration> getMethodConfigs() {
        return methodConfigs;
    }

    public void setMethodConfigs(Map<String, CircuitBreakerConfiguration.CircuitBreakerMethodConfiguration> methodConfigs) {
        this.methodConfigs = Collections.unmodifiableMap(methodConfigs);
    }

//    public Map<String, CircuitBreakerConfiguration.CircuitBreakerServiceConfiguration> getServiceConfigs() {
//        return serviceConfigs;
//    }
//
//    public void setServiceConfigs(Map<String, CircuitBreakerConfiguration.CircuitBreakerServiceConfiguration> serviceConfigs) {
//        this.serviceConfigs = serviceConfigs;
//    }

    private CircuitBreakerMethodConfigs createCircuitBreakerConfigs() {
        CircuitBreakerMethodConfigs configs = new CircuitBreakerMethodConfigs();

        methodConfigs.forEach((key, value) -> configs.addWrappedConfig(key, createCircuitBreakerConfigurationWrapper(value)));
        //serviceConfigs.forEach((key, value) -> configs.addWrappedConfig(key, createCircuitBreakerConfigurationWrapper(value)));

        return configs;
    }

    private CircuitBreakerConfigurationWrapper createCircuitBreakerConfigurationWrapper(CircuitBreakerConfiguration circuitBreakerConfiguration) {
        return CircuitBreakerConfigurationWrapper.CircuitBreakerConfigurationWrapperBuilder.builder()
            .commandName(circuitBreakerConfiguration.getCommandName().orElse(null))
            .groupName(circuitBreakerConfiguration.getGroupName().orElse(null))
            .circuitBreakerConfiguration(circuitBreakerConfiguration)
            .build();
    }

}
