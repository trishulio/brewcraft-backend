package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.StorageAccessor;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedStorageRepositoryImpl implements EnhancedStorageRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedStorageRepositoryImpl.class);
    
    private StorageRepository storageRepo;
    
    public EnhancedStorageRepositoryImpl(StorageRepository storageRepo) {
        this.storageRepo = storageRepo;
    }

    @Override
    public void refreshAccessor(Collection<? extends StorageAccessor> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<Long, List<StorageAccessor>> lookupAccessorsByStorageId = accessors.stream().filter(accessor -> accessor != null && accessor.getStorage() != null).collect(Collectors.groupingBy(accessor -> accessor.getStorage().getId()));

            List<Storage> storages = storageRepo.findAllById(lookupAccessorsByStorageId.keySet());

            if (lookupAccessorsByStorageId.keySet().size() != storages.size()) {
                List<Long> storageIds = storages.stream().map(material -> material.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all storage in Id-Set: %s. Storages found with Ids: %s", lookupAccessorsByStorageId.keySet(), storageIds));
            }

            accessors.forEach(accessor -> accessor.setStorage(null));
            storages.forEach(storage -> lookupAccessorsByStorageId.get(storage.getId()).forEach(accessor -> accessor.setStorage(storage)));
        }
    }
}
