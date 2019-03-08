package com.yg0r2.factorio.recipe.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Ingredient.Builder.class)
public class Ingredient {

    private final String name;
    private final int amount;

    public Ingredient(Builder builder) {
        name = builder.name;
        amount = builder.amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public static class Builder {

        private String name;
        private int amount;

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withAmount(int amount) {
            this.amount = amount;

            return this;
        }

        public Ingredient build() {
            return new Ingredient(this);
        }
    }
}
