package com.yg0r2.common.configresolver.service;

import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotels.platform.config.domain.Context;
import com.hotels.platform.config.domain.NoPropertyDefinitionFoundException;

public class ConfigurationResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationResolver.class);

    private final com.hotels.platform.config.resolution.ConfigurationResolver configurationResolver;

    public ConfigurationResolver(com.hotels.platform.config.resolution.ConfigurationResolver configurationResolver) {
        this.configurationResolver = configurationResolver;
    }

    public int intValue(String propertyName, Context context) {
        return resolve(propertyName, context, Integer::parseInt, 0);
    }

    public boolean is(String propertyName, Context context) {
        return resolve(propertyName, context, Boolean::parseBoolean, false);
    }

    public String get(String propertyName, Context context) {
        return resolve(propertyName, context, String::valueOf, null);
    }

    public <T> T resolve(String propertyName, Context context, Function<String, T> mappingFunction, T defaultValue) {
        return resolve(propertyName, context)
            .map(mappingFunction)
            .orElse(defaultValue);
    }

    private Optional<String> resolve(String propertyName, Context context) {
        try {
            return configurationResolver.resolve(propertyName, context);
        }
        catch (NoPropertyDefinitionFoundException exception) {
            LOGGER.warn(exception.getMessage(), exception);
        }

        return Optional.empty();
    }

}
