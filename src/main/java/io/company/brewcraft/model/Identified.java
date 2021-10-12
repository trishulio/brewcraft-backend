package io.company.brewcraft.model;

public interface Identified<T> {
    final String ATTR_ID = "id";

    T getId();
}
