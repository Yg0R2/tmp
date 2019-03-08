package com.yg0r2.factorio.recipe.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yg0r2.factorio.machine.domain.MachineType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(builder = Recipe.Builder.class)
public class Recipe {

    private final String name;
    private final int production;
    private final double time;
    private final MachineType machineType;
    private final List<Ingredient> ingredients;

    private Recipe(Builder builder) {
        name = builder.name;
        production = builder.production;
        time = builder.time;
        machineType = builder.machineType;
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

    public MachineType getMachineType() {
        return machineType;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Builder {

        private String name;
        private int production;
        private double time;
        @JsonProperty("producerMachineType")
        private MachineType machineType;
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

        public Builder withMachineType(MachineType machineType) {
            this.machineType = machineType;

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
