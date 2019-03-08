package com.yg0r2.factorio.web;

import com.yg0r2.factorio.service.DisplayRecipeService;
import com.yg0r2.factorio.web.domain.DisplayRecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api")
public class RecipesController {

    @Autowired
    private DisplayRecipeService displayRecipeService;

    @GetMapping(params = {"recipeName", "amountPerSec"})
    public DisplayRecipe getRecipe(@NotBlank @RequestParam String recipeName, @Min(0) @RequestParam double amountPerSec) {
        return displayRecipeService.getByName(recipeName, amountPerSec);
    }

}
