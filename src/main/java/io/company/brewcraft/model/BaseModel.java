package io.company.brewcraft.model;

import java.util.Collections;
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

    public void outerJoin(BaseModel other) {
        util.outerJoin(this, other, pd -> pd.getReadMethod().invoke(this) == null);
    }

    public void override(BaseModel other, Set<String> exclude) {
        util.outerJoin(this, other, pd -> !exclude.contains(pd.getName()));
    }

    public void override(BaseModel other) {
        override(other, Collections.emptySet());
    }

    @Override
    public boolean equals(Object o) {
        return util.equals(this, o);
    }
}
