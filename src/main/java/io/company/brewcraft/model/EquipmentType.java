package io.company.brewcraft.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EquipmentType {
    BARREL("Barrel"),
    BOIL_KETTLE("Boil Kettle"),
    BRITE_TANK("Brite Tank"),
    FERMENTER("Fermenter"),
    MIX_TANK("Mix Tank"),
    SERVING_TANK("Serving Tank"),
    TOTE("Tote"),
    WHIRLPOOL("Whirl Pool");

    private final String name;

    EquipmentType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getEquipmentTypeName() {
        return this.name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
