package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.service.StorageAccessor;

public interface EnhancedStorageRepository {
    void refreshAccessor(Collection<? extends StorageAccessor> accessors);
}
