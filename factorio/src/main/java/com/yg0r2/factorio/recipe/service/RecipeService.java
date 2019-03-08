package com.yg0r2.factorio.recipe.service;

import com.yg0r2.factorio.recipe.domain.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    @Autowired
    private Map<String, Recipe> recipeMap;

    public Recipe getByName(String recipeName) {
        return recipeMap.get(recipeName);
    }

}
