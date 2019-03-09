package com.yg0r2.factorio.recipe.service;

import com.yg0r2.factorio.recipe.domain.Recipe;
import com.yg0r2.factorio.recipe.service.exceptions.RecipeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private Map<String, Recipe> recipeMap;

    public Recipe getByName(String recipeName) {
        return Optional.of(recipeName)
            .map(recipeMap::get)
            .orElseThrow(() -> new RecipeNotFoundException("Recipe doesn't exist with name: " + recipeName));
    }

}
