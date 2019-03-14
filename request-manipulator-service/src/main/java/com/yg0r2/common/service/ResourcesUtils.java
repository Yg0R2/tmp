package com.yg0r2.common.service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ResourcesUtils<T> {

    private final TypeReference<T> typeReference;
    private final ObjectMapper objectMapper;

    public ResourcesUtils(TypeReference<T> typeReference, ObjectMapper objectMapper) {
        this.typeReference = typeReference;
        this.objectMapper = objectMapper;
    }

    public List<T> read(String locationPattern) {
        return Arrays.stream(getResources(locationPattern))
            .map(this::getURL)
            .map(this::read)
            .collect(Collectors.toList());
    }

    private Resource[] getResources(String locationPattern) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        try {
            return resourcePatternResolver.getResources(locationPattern);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getURL(Resource resource) {
        try {
            return resource.getURL();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private T read(URL url) {
        try {
            return objectMapper.readValue(url, typeReference);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
