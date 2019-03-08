package com.yg0r2.factorio.service;

import com.yg0r2.factorio.recipe.service.RecipeService;
import com.yg0r2.factorio.web.domain.DisplayRecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DisplayRecipeService {

    @Autowired
    private DisplayRecipeFactory displayRecipeFactory;
    @Autowired
    private RecipeService recipeService;

    public DisplayRecipe getByName(String recipeName, double amountPerSec) {
        return Optional.ofNullable(recipeName)
            .map(recipeService::getByName)
            .map(recipe -> displayRecipeFactory.create(recipe, amountPerSec))
            .orElse(null);
    }

}
