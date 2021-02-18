package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateStorage;
import io.company.brewcraft.model.StorageEntity;
import io.company.brewcraft.pojo.Facility;
import io.company.brewcraft.pojo.Storage;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.StorageMapper;

@Transactional
public class StorageServiceImpl extends BaseService implements StorageService {
    public static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);
    
    private static final StorageMapper storageMapper = StorageMapper.INSTANCE;

    private StorageRepository storageRepository;
    
    private FacilityService facilityService;
            
    public StorageServiceImpl(StorageRepository storageRepository, FacilityService facilityService) {
        this.storageRepository = storageRepository;
        this.facilityService = facilityService;
    }

    @Override
    public Page<Storage> getAllStorages(int page, int size, Set<String> sort, boolean orderAscending) {        
        Page<StorageEntity> storagePage = storageRepository.findAll(pageRequest(sort, orderAscending, page, size));
        
        return storagePage.map(storage -> storageMapper.fromEntity(storage, new CycleAvoidingMappingContext()));
    }
    
    @Override
    public Storage getStorage(Long storageId) {
        StorageEntity storage = storageRepository.findById(storageId).orElse(null);
        
        return storageMapper.fromEntity(storage, new CycleAvoidingMappingContext());
    }

    @Override
    public Storage addStorage(Long facilityId, Storage storage) {    
        if (facilityId != null) {
            Facility facility = Optional.ofNullable(facilityService.getFacility(facilityId)).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
            storage.setFacility(facility);
        }
        
        StorageEntity storageEntity = storageMapper.toEntity(storage, new CycleAvoidingMappingContext());
        
        StorageEntity addedStorage = storageRepository.save(storageEntity);
        
        return storageMapper.fromEntity(addedStorage, new CycleAvoidingMappingContext());
    }

    @Override
    public Storage putStorage(Long facilityId, Long storageId, UpdateStorage updatedStorage) {   
        Storage existing = getStorage(storageId);

        if (existing == null) {
            existing = new Storage();
            existing.setId(storageId);
            existing.setCreatedAt(LocalDateTime.now()); // TODO: This is a hack. Need a fix at hibernate level to avoid any hibernate issues.
        }

        existing.override(updatedStorage, getPropertyNames(UpdateStorage.class));

        return addStorage(facilityId, existing);
    }
    
    @Override
    public Storage patchStorage(Long storageId, UpdateStorage updatedStorage) {        
        Storage existing = Optional.ofNullable(getStorage(storageId)).orElseThrow(() -> new EntityNotFoundException("Storage", storageId.toString()));      

        existing.outerJoin(updatedStorage, getPropertyNames(UpdateStorage.class));
        
        return addStorage(existing.getFacility().getId(), existing);
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
