package com.yg0r2.factorio.web.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JsonDeserialize(builder = DisplayRecipe.Builder.class)
public class DisplayRecipe {

    private final String name;
    private final Map<String, Integer> machinesCountMap;
    private final List<DisplayRecipe> recipes;

    private DisplayRecipe(Builder builder) {
        name = builder.name;
        machinesCountMap = Map.copyOf(builder.machinesCountMap);
        recipes = Optional.ofNullable(builder.recipes).orElse(Collections.emptyList());
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getMachinesCountMap() {
        return machinesCountMap;
    }

    public List<DisplayRecipe> getRecipes() {
        return recipes;
    }

    public static class Builder {

        private String name;
        private Map<String, Integer> machinesCountMap;
        private List<DisplayRecipe> recipes;

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withMachinesCountMap(Map<String, Integer> machinesCountMap) {
            this.machinesCountMap = machinesCountMap;

            return this;
        }

        public Builder withDisplayRecipe(List<DisplayRecipe> recipes) {
            this.recipes = recipes;

            return this;
        }

        public DisplayRecipe build() {
            return new DisplayRecipe(this);
        }
    }
    
}
