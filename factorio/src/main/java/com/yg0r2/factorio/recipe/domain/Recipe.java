package com.yg0r2.factorio.recipe.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(builder = Recipe.Builder.class)
public class Recipe {

    private final String name;
    private final int production;
    private final double time;
    private final List<String> machineNames;
    private final List<Ingredient> ingredients;

    private Recipe(Builder builder) {
        name = builder.name;
        production = builder.production;
        time = builder.time;
        machineNames = List.copyOf(builder.machineNames);
        ingredients = Optional.ofNullable(builder.ingredients).map(List::copyOf).orElse(Collections.emptyList());
    }

    public String getName() {
        return name;
    }

    public int getProduction() {
        return production;
    }

    public double getTime() {
        return time;
    }

    public List<String> getMachineNames() {
        return machineNames;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Builder {

        private String name;
        private int production;
        private double time;
        private List<String> machineNames;
        private List<Ingredient> ingredients;

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withProduction(int production) {
            this.production = production;

            return this;
        }

        public Builder withTime(double time) {
            this.time = time;

            return this;
        }

        public Builder withMachineNames(List<String> machineNames) {
            this.machineNames = machineNames;

            return this;
        }

        public Builder withIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;

            return this;
        }

        public Recipe build() {
            return new Recipe(this);
        }
    }

}
