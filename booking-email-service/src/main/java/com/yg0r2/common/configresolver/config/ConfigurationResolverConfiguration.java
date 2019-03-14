package com.yg0r2.common.configresolver.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hotels.platform.config.resolution.ConfigurationResolverBuilder;
import com.yg0r2.common.configresolver.service.ConfigurationResolver;

@Configuration
public class ConfigurationResolverConfiguration {

    @Value("#{'${cf.config.service.packs}'.split(',')}")
    private List<String> configPacks;

    @Bean
    public ConfigurationResolver configurationResolver() {
        return new ConfigurationResolver(createConfigurationResolver());
    }

    private com.hotels.platform.config.resolution.ConfigurationResolver createConfigurationResolver() {
        return new ConfigurationResolverBuilder(configPacks)
            .withClasspathConfigSource()
            .build();
    }

}
