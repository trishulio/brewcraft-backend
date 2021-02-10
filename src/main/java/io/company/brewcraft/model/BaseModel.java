package io.company.brewcraft.model;

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

    public void override(Object other) {
        util.copy(this, other, pd -> true);
    }

    public void override(Object other, Set<String> include) {
        util.copy(this, other, pd -> include.contains(pd.getName()));
    }

    public Set<String> getProps(Class<?> clazz) {
        return util.getPropertyNames(clazz);
    }

    @Override
    public boolean equals(Object o) {
        return util.equals(this, o);
    }
}
