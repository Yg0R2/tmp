package com.yg0r2.factorio.recipe.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yg0r2.factorio.recipe.domain.Recipe;
import com.yg0r2.factorio.utils.ResourcesUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class RecipesConfiguration {

    private static final String RECIPES_RESOURCE_PATTERN = "classpath*:recipes/*.json";

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public Map<String, Recipe> recipesMap() {
        return resourcesUtils().read(RECIPES_RESOURCE_PATTERN).stream()
            .collect(Collectors.toMap(recipe -> recipe.getName(), Function.identity()));
    }

    private ResourcesUtils<Recipe> resourcesUtils() {
        return new ResourcesUtils<>(new TypeReference<>() {}, objectMapper);
    }

}
