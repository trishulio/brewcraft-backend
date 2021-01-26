package io.company.brewcraft.model;

import io.company.brewcraft.util.entity.ReflectionManipulator;

public abstract class BaseModel {
    protected ReflectionManipulator util;

    protected BaseModel() {
        this(ReflectionManipulator.INSTANCE);
    }

    protected BaseModel(ReflectionManipulator util) {
        this.util = util;
    }

    public void outerJoin(BaseModel other) {
        util.outerJoin(this, other, (getter, setter) -> getter.invoke(this) == null);
    }

    @Override
    public boolean equals(Object o) {
        return util.equals(this, o);
    }
}
