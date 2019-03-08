package com.yg0r2.factorio.machine.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MachineType {

    ASSEMBLER("assembler"), FURNACE("furnace");

    private final String typeName;

    MachineType(String typeName) {
        this.typeName = typeName;
    }

    @JsonValue
    public String getTypeName() {
        return typeName;
    }

}
