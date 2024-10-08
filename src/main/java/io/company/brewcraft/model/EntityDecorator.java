package io.company.brewcraft.model;

import java.util.List;

public interface EntityDecorator<T> {
    <R extends T> void decorate(List<R> entities);
}
