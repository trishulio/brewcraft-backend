package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.StorageEntity;

public interface StorageService {

    public Page<StorageEntity> getAllStorages(int page, int size, Set<String> sort, boolean orderAscending);
    
    public StorageEntity getStorage(Long storageId);

    public StorageEntity addStorage(Long facilityId, StorageEntity storage);
    
    public StorageEntity putStorage(Long facilityId, Long storageId, StorageEntity storage);
    
    public StorageEntity patchStorage(Long storageId, StorageEntity storage);

    public void deleteStorage(Long storageId); 
    
    public boolean storageExists(Long storageId);
    
}
