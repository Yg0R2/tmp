package com.yg0r2.factorio.service;

import com.yg0r2.factorio.recipe.domain.Ingredient;
import com.yg0r2.factorio.recipe.domain.Recipe;
import com.yg0r2.factorio.recipe.service.RecipeService;
import com.yg0r2.factorio.web.domain.DisplayRecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DisplayRecipeFactory {

    @Autowired
    private RecipeService recipeService;

    public DisplayRecipe create(Recipe recipe, double amountPerSec) {
        String recipeName = recipe.getName();
        double requiredAssemblerCount = amountPerSec / (recipe.getProduction() / recipe.getTime());

        return new DisplayRecipe.Builder()
            .withName(recipeName)
            .withAssemblerCount((int) Math.ceil(requiredAssemblerCount))
            .withDisplayRecipe(getIngredientRecipes(recipe.getIngredients(), requiredAssemblerCount / recipe.getTime()))
            .build();
    }

    private List<DisplayRecipe> getIngredientRecipes(List<Ingredient> ingredients, double amountPerSec) {
        return ingredients.stream()
            .map(Ingredient::getName)
            .map(recipeService::getByName)
            .map(ingredientsRecipe -> create(ingredientsRecipe, amountPerSec) )
            .collect(Collectors.toList());
    }

}
