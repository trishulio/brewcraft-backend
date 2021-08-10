package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.StorageAccessor;

public class EnhancedStorageRepositoryImpl implements EnhancedStorageRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedStorageRepositoryImpl.class);

    private AccessorRefresher<Long, StorageAccessor, Storage> refresher;

    public EnhancedStorageRepositoryImpl(AccessorRefresher<Long, StorageAccessor, Storage> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends StorageAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
