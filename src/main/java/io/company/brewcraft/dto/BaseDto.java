package io.company.brewcraft.dto;

import io.company.brewcraft.util.entity.ReflectionManipulator;

public abstract class BaseDto {

    private ReflectionManipulator util;

    protected BaseDto() {
        this(ReflectionManipulator.INSTANCE);
    }

    protected BaseDto(ReflectionManipulator util) {
        this.util = util;
    }

    @Override
    public boolean equals(Object o) {
        return util.equals(this, o);
    }

    @Override
    public String toString() {
        // TODO:
        return "JSON";
    }
    
    public static <T> T fromString(String s, Class<T> clazz) {
        // TODO:
        return null;
    }
}
