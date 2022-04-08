package io.company.brewcraft.controller;

import java.util.List;

import io.company.brewcraft.model.EntityDecorator;

public class NoActionDecorator<T> implements EntityDecorator<T>{
    @Override
    public <R extends T> void decorate(List<R> entities) {
        // Does nothing.
    }
}
