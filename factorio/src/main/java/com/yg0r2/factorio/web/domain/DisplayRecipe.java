package com.yg0r2.factorio.web.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@JsonDeserialize(builder = DisplayRecipe.Builder.class)
public class DisplayRecipe {

    private String name;
    private int assemblerCount;
    private List<DisplayRecipe> recipes;

    private DisplayRecipe(Builder builder) {
        name = builder.name;
        assemblerCount = builder.assemblerCount;
        recipes = Optional.ofNullable(builder.recipes).orElse(Collections.emptyList());
    }

    public String getName() {
        return name;
    }

    public int getAssemblerCount() {
        return assemblerCount;
    }

    public List<DisplayRecipe> getRecipes() {
        return recipes;
    }

    public static class Builder {

        private String name;
        private int assemblerCount;
        private List<DisplayRecipe> recipes;

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withAssemblerCount(int assemblerCount) {
            this.assemblerCount = assemblerCount;

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
