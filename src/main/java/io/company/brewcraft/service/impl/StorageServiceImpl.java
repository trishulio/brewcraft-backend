package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Optional;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class StorageServiceImpl extends BaseService implements StorageService {
    private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);

    private StorageRepository storageRepository;

    private FacilityService facilityService;

    public StorageServiceImpl(StorageRepository storageRepository, FacilityService facilityService) {
        this.storageRepository = storageRepository;
        this.facilityService = facilityService;
    }

    @Override
    public Page<Storage> getAllStorages(int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Page<Storage> storagePage = storageRepository.findAll(pageRequest(sort, orderAscending, page, size));

        return storagePage;
    }

    @Override
    public Storage getStorage(Long storageId) {
        Storage storage = storageRepository.findById(storageId).orElse(null);

        return storage;
    }

    @Override
    public Storage addStorage(Long facilityId, Storage storage) {
        if (facilityId != null) {
            Facility facility = Optional.ofNullable(facilityService.getFacility(facilityId)).orElseThrow(() -> new EntityNotFoundException("Facility", facilityId.toString()));
            storage.setFacility(facility);
        }

        Storage addedStorage = storageRepository.saveAndFlush(storage);

        return addedStorage;
    }

    @Override
    public Storage putStorage(Long facilityId, Long storageId, Storage updatedStorage) {
        updatedStorage.setId(storageId);

        return addStorage(facilityId, updatedStorage);
    }

    @Override
    public Storage patchStorage(Long storageId, Storage updatedStorage) {
        Storage existingStorage = Optional.ofNullable(getStorage(storageId)).orElseThrow(() -> new EntityNotFoundException("Storage", storageId.toString()));

        updatedStorage.copyToNullFields(existingStorage);

        return addStorage(existingStorage.getFacility().getId(), updatedStorage);
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
