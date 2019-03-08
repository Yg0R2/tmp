package com.yg0r2.factorio.machine.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.factorio.machine.domain.Machine;
import com.yg0r2.factorio.utils.ResourcesUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class MachinesConfiguration {

    private static final String MACHINES_RESOURCE_PATTERN = "classpath*:machines/*.json";

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public Map<String, Machine> machinesMap() {
        return resourcesUtils().read(MACHINES_RESOURCE_PATTERN).stream()
            .collect(Collectors.toMap(machine -> machine.getName(), Function.identity()));
    }

    private ResourcesUtils<Machine> resourcesUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

}
