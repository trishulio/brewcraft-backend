package io.company.brewcraft.model;

import javax.persistence.OptimisticLockException;

public interface Versioned {
    final String FIELD_VERSION = "version";

    Integer getVersion();

    void setVersion(Integer version);

    default void optimisicLockCheck(Versioned update) {
        if (this.getVersion() != update.getVersion()) {
            throw new OptimisticLockException(String.format("Cannot update entity with of version: %s with update payload of version: %s", this.getVersion(), update.getVersion()));
        }
    }
}
