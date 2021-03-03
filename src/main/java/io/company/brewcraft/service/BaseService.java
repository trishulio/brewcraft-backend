package io.company.brewcraft.service;

import java.time.LocalDateTime;
import java.util.Set;

import io.company.brewcraft.util.entity.ReflectionManipulator;
import io.company.brewcraft.util.validator.Validator;

public class BaseService {
    protected ReflectionManipulator util;

    public BaseService() {
        this(ReflectionManipulator.INSTANCE);
    }

    protected BaseService(ReflectionManipulator util) {
        this.util = util;
    }

    public Set<String> getPropertyNames(Class<?> clazz) {
        return util.getPropertyNames(clazz);
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }

    public Validator validator() {
        return new Validator();
    }
}
