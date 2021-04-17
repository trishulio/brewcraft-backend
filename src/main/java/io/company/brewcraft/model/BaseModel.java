package io.company.brewcraft.model;

import java.lang.reflect.Field;
import java.util.Set;

import io.company.brewcraft.util.entity.ReflectionManipulator;

public abstract class BaseModel {
    protected ReflectionManipulator util;

    protected BaseModel() {
        this(ReflectionManipulator.INSTANCE);
    }

    protected BaseModel(ReflectionManipulator util) {
        this.util = util;
    }

    public void outerJoin(Object other) {
        util.copy(this, other, pd -> pd.getReadMethod().invoke(other) != null);
    }

    public void outerJoin(Object other, Set<String> include) {
        util.copy(this, other, pd -> include.contains(pd.getName()) && pd.getReadMethod().invoke(other) != null);
    }
    
    public void copyToNullFields(Object existingEntity) {
        //TODO: util.copy below causing unit test to fail, need to investigate why
        //util.copy(this, existingEntity, pd -> pd.getReadMethod().invoke(this) == null);
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(this);

                if (value == null) {
                    field.set(this, field.get(existingEntity));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void override(Object other) {
        util.copy(this, other, pd -> true);
    }

    public void override(Object other, Set<String> include) {
        util.copy(this, other, pd -> include.contains(pd.getName()));
    }

    @Override
    public boolean equals(Object o) {
        return util.equals(this, o);
    }
}
