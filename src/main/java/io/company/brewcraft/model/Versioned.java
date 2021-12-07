package io.company.brewcraft.model;

import javax.persistence.OptimisticLockException;

public interface Versioned {
    final String ATTR_VERSION = "version";

    Integer getVersion();

    default void optimisticLockCheck(Versioned update) {
        if (this.getVersion() != update.getVersion()) {
            throw new OptimisticLockException(String.format("Cannot update entity of version: %s with update payload of version: %s", this.getVersion(), update.getVersion()));
        }
    }
}
