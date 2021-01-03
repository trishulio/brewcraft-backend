package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class StorageServiceImpl implements StorageService {
    public static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private StorageRepository storageRepository;
    
    private FacilityRepository facilityRepository;
            
    public StorageServiceImpl(StorageRepository storageRepository, FacilityRepository facilityRepository) {
        this.storageRepository = storageRepository;
        this.facilityRepository = facilityRepository;
    }

    @Override
    public Page<Storage> getAllStorages(int page, int size, Set<String> sort, boolean orderAscending) {        
        return storageRepository.findAll(pageRequest(sort, orderAscending, page, size));
    }
    
    @Override
    public Storage getStorage(Long storageId) {
        Storage storage = storageRepository.findById(storageId).orElse(null);
        
        return storage;
    }

    @Override
    public Storage addStorage(Long facilityId, Storage storage) {         
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
        
        storage.setFacility(facility);
        
        return storageRepository.save(storage);
    }

    @Override
    public Storage putStorage(Long storageId, Storage updatedStorage) {   
        Storage storage = storageRepository.findById(storageId).orElse(null);
        
        if (storage != null) {
            updatedStorage.outerJoin(storage);
        }
        
        updatedStorage.setId(storageId);

        return storageRepository.save(updatedStorage);
    }
    
    @Override
    public Storage patchStorage(Long storageId, Storage updatedStorage) {        
        Storage storage = storageRepository.findById(storageId).orElseThrow(() -> new EntityNotFoundException("Storage", storageId.toString()));      
        
        updatedStorage.outerJoin(storage);

        return storageRepository.save(updatedStorage);
    }

    @Override
    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);        
    } 
    
    @Override
    public boolean storageExists(Long storageId) {
        return storageRepository.existsById(storageId);
    }
    
}
