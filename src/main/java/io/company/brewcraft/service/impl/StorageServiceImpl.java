package io.company.brewcraft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    public Page<Storage> getAllStorages(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));
        
        return storageRepository.findAll(paging);
    }
    
    @Override
    public Storage getStorage(Long storageId) {
        Storage storage = storageRepository.findById(storageId).get();
        
        return storage;
    }

    @Override
    public void addStorage(Long facilityId, Storage storage) {         
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
        
        storage.setFacility(facility);
        
        storageRepository.save(storage);
    }

    @Override
    public void putStorage(Long storageId, Storage updatedStorage) {   
        Storage storage = storageRepository.findById(storageId).orElse(null);
        
        if (storage != null) {
            updatedStorage.outerJoin(storage);
        }
        
        updatedStorage.setId(storageId);

        storageRepository.save(updatedStorage);
    }
    
    @Override
    public void patchStorage(Long storageId, Storage updatedStorage) {        
        Storage storage = storageRepository.findById(storageId).orElseThrow(() -> new EntityNotFoundException("Storage", storageId.toString()));      
        
        updatedStorage.outerJoin(storage);

        storageRepository.save(updatedStorage);
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
