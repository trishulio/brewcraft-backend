package io.company.brewcraft.model;

import io.company.brewcraft.util.entity.ReflectionManipulator;

public abstract class BaseEntity {

    protected ReflectionManipulator util;

    protected BaseEntity() {
        this(ReflectionManipulator.INSTANCE);
    }

    protected BaseEntity(ReflectionManipulator util) {
        this.util = util;
    }

    public void outerJoin(BaseEntity other) {
        util.outerJoin(this, other, (getter, setter) -> getter.invoke(this) == null);
    }

    @Override
    public boolean equals(Object o) {
        return util.equals(this, o);
    }
}
