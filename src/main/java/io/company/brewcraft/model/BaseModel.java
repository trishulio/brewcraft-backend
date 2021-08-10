package io.company.brewcraft.model;

import java.util.Set;

import io.company.brewcraft.util.JsonMapper;
import io.company.brewcraft.util.entity.ReflectionManipulator;

public abstract class BaseModel {
    protected ReflectionManipulator util;
    protected JsonMapper jsonMapper;

    protected BaseModel() {
        this(ReflectionManipulator.INSTANCE, JsonMapper.INSTANCE);
    }

    protected BaseModel(ReflectionManipulator util, JsonMapper jsonMapper) {
        this.util = util;
        this.jsonMapper = jsonMapper;
    }

    public void outerJoin(Object other) {
        util.copy(this, other, pd -> pd.getReadMethod().invoke(other) != null);
    }

    public void outerJoin(Object other, Set<String> include) {
        util.copy(this, other, pd -> include.contains(pd.getName()) && pd.getReadMethod().invoke(other) != null);
    }

    public void copyToNullFields(Object existingEntity) {
        util.copy(this, existingEntity, pd -> pd.getReadMethod().invoke(this) == null);
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

    @Override
    public String toString() {
        return jsonMapper.writeString(this);
    }
}
