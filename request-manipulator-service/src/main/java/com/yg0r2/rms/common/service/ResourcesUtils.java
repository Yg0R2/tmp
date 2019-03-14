package com.yg0r2.rms.common.service;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ResourcesUtils<T> {

    private final TypeReference<T> typeReference;
    private final ObjectMapper objectMapper;
    private final ResourcePatternResolver resourcePatternResolver;

    public ResourcesUtils(TypeReference<T> typeReference, ObjectMapper objectMapper) {
        this.typeReference = typeReference;
        this.objectMapper = objectMapper;

        resourcePatternResolver = new PathMatchingResourcePatternResolver();
    }

    public T read(String location) {
        return Optional.of(location)
            .map(resourcePatternResolver::getResource)
            .map(this::getURL)
            .map(this::read)
            .orElseThrow(() -> new RuntimeException("Error during reading " + location));
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
