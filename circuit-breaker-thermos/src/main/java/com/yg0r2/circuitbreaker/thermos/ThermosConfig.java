package com.yg0r2.circuitbreaker.thermos;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hotels.thermos.CircuitBreakerConfiguration;
import com.hotels.thermos.DefaultThermosEngine;
import com.hotels.thermos.ThermosEngine;
import com.hotels.thermos.spring.circuitbreaker.CircuitBreakerMethodConfigs;
import com.hotels.thermos.spring.config.ThermosSpringConfig;

@Configuration
@ConfigurationProperties(prefix = "thermos")
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

        methodConfigs.forEach(configs::addConfig);

        return configs;
    }
}
