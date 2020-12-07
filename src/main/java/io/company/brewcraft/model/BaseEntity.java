package io.company.brewcraft.model;

import io.company.brewcraft.util.entity.ReflectionManipulator;

public abstract class BaseEntity extends BaseModel {

    protected BaseEntity() {
        super();
    }

    protected BaseEntity(ReflectionManipulator util) {
        super(util);
    }
}
