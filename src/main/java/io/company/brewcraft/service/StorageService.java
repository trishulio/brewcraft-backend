package io.company.brewcraft.service;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Storage;

public interface StorageService {

    public Page<Storage> getAllStorages(int page, int size, String[] sort, boolean order_asc);
    
    public Storage getStorage(Long storageId);

    public void addStorage(Long facilityId, Storage storage);
    
    public void putStorage(Long storageId, Storage storage);
    
    public void patchStorage(Long storageId, Storage storage);

    public void deleteStorage(Long storageId); 
    
    public boolean storageExists(Long storageId);
    
}
