package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.StorageAccessor;

public interface EnhancedStorageRepository {
    void refreshAccessors(Collection<? extends StorageAccessor> accessors);
}
