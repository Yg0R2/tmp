package com.yg0r2.factorio.recipe.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import com.yg0r2.factorio.service.DisplayRecipeFactory;
import com.yg0r2.factorio.web.domain.DisplayRecipe;
import com.yg0r2.factorio.recipe.domain.Ingredient;
import com.yg0r2.factorio.recipe.domain.Recipe;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DisplayRecipeFactoryTest {

    private static final String RECIPE_NAME = "Transport_belt";
    private static final String INGREDIENT_NAME = "Iron_gear_wheel";

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private DisplayRecipeFactory underTest;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransform() {
        Recipe recipe = createRecipe(RECIPE_NAME, 2,0.5, null);

        DisplayRecipe actual = underTest.create(recipe, 1);

        verifyZeroInteractions(recipeService);

        assertEquals(actual.getName(), recipe.getName());
        assertEquals(actual.getAssemblerCount(), 1);
    }

    @Test
    public void testTransformMultiLevel() {
        Recipe recipe1 = createRecipe(INGREDIENT_NAME, 1,0.5, null);
        Recipe recipe2 = createRecipe(RECIPE_NAME, 2,0.5, List.of(createIngredient(INGREDIENT_NAME, 1)));

        when(recipeService.getByName(INGREDIENT_NAME)).thenReturn(recipe1);

        DisplayRecipe actual = underTest.create(recipe2, 4);

        verify(recipeService).getByName(INGREDIENT_NAME);
        verifyNoMoreInteractions(recipeService);

        assertEquals(actual.getName(), RECIPE_NAME);
        assertEquals(actual.getAssemblerCount(), 1);
        assertEquals(actual.getRecipes().get(0).getName(), INGREDIENT_NAME);
        assertEquals(actual.getRecipes().get(0).getAssemblerCount(), 1);
    }

    private Recipe createRecipe(String name, int production, double time, List<Ingredient> ingredients) {
        return new Recipe.Builder()
            .withName(name)
            .withProduction(production)
            .withTime(time)
            .withIngredients(ingredients)
            .build();
    }

    private Ingredient createIngredient(String name, int amount) {
        return new Ingredient.Builder()
            .withName(name)
            .withAmount(amount)
            .build();
    }

}
