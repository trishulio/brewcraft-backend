package io.company.brewcraft.model;

public interface IdentityAccessor<T> extends Identified<T> {
    void setId(T id);
}
