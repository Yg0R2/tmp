package com.yg0r2.factorio.service;

import com.yg0r2.factorio.machine.service.MachineService;
import com.yg0r2.factorio.recipe.domain.Ingredient;
import com.yg0r2.factorio.recipe.domain.Recipe;
import com.yg0r2.factorio.recipe.service.RecipeService;
import com.yg0r2.factorio.web.domain.DisplayRecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DisplayRecipeFactory {

    @Autowired
    private MachineService machineService;
    @Autowired
    private RecipeService recipeService;

    public DisplayRecipe create(Recipe recipe, double amountPerSec) {
        String recipeName = recipe.getName();
        double machineCount = amountPerSec / (recipe.getProduction() / recipe.getTime());

        return new DisplayRecipe.Builder()
            .withName(recipeName)
            .withMachinesCountMap(getMachinesCountMap(recipe.getMachineNames(), machineCount))
            .withDisplayRecipe(getIngredientRecipes(recipe.getIngredients(), machineCount / recipe.getTime()))
            .build();
    }

    private Map<String, Integer> getMachinesCountMap(List<String> machineNames, double machineCount) {
        return machineNames.stream()
            .map(machineService::getByName)
            .collect(Collectors.toMap(
                    machine -> machine.getName(),
                    machine -> (int) Math.ceil(machine.getCraftingSpeed() * machineCount)));
    }

    private List<DisplayRecipe> getIngredientRecipes(List<Ingredient> ingredients, double amountPerSec) {
        return ingredients.stream()
            .map(Ingredient::getName)
            .map(recipeService::getByName)
            .map(ingredientsRecipe -> create(ingredientsRecipe, amountPerSec) )
            .collect(Collectors.toList());
    }

}
