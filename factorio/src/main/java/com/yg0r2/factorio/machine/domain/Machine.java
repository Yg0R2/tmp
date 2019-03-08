package com.yg0r2.factorio.machine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = Machine.Builder.class)
public class Machine {

    private final String name;
    private final double craftingSpeed;
    private final MachineType machineType;

    private Machine(Builder builder) {
        name = builder.name;
        craftingSpeed = builder.craftingSpeed;
        machineType = builder.machineType;
    }

    public String getName() {
        return name;
    }

    public double getCraftingSpeed() {
        return craftingSpeed;
    }


    public MachineType getMachineType() {
        return machineType;
    }

    public static class Builder {

        public String name;
        private double craftingSpeed;
        @JsonProperty("type")
        private MachineType machineType;

        public Builder withName(String name) {
            this.name = name;

            return this;
        }

        public Builder withCraftingSpeed(double craftingSpeed) {
            this.craftingSpeed = craftingSpeed;

            return this;
        }

        public Builder withMachineType(MachineType machineType) {
            this.machineType = machineType;

            return this;
        }

        public Machine build() {
            return new Machine(this);
        }
    }

}
