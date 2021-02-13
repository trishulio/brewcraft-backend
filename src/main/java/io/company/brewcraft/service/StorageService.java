package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateStorage;
import io.company.brewcraft.pojo.Storage;

public interface StorageService {

    public Page<Storage> getAllStorages(int page, int size, Set<String> sort, boolean orderAscending);
    
    public Storage getStorage(Long storageId);

    public Storage addStorage(Long facilityId, Storage storage);
    
    public Storage putStorage(Long facilityId, Long storageId, UpdateStorage storage);
    
    public Storage patchStorage(Long storageId, UpdateStorage storage);

    public void deleteStorage(Long storageId); 
    
    public boolean storageExists(Long storageId);
    
}
