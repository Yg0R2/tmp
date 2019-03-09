package com.yg0r2.factorio.machine.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonDeserialize(builder = Machine.Builder.class)
public class Machine {

    private final String name;
    private final double craftingSpeed;

    private Machine(Builder builder) {
        name = builder.name;
        craftingSpeed = builder.craftingSpeed;
    }

    public String getName() {
        return name;
    }

    public double getCraftingSpeed() {
        return craftingSpeed;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Builder {

        public String name;
        private double craftingSpeed;

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withCraftingSpeed(double craftingSpeed) {
            this.craftingSpeed = craftingSpeed;

            return this;
        }

        public Machine build() {
            return new Machine(this);
        }
    }

}
