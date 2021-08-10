package io.company.brewcraft.service;

import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Storage;

public interface StorageService {

    public Page<Storage> getAllStorages(int page, int size, SortedSet<String> sort, boolean orderAscending);

    public Storage getStorage(Long storageId);

    public Storage addStorage(Long facilityId, Storage storage);

    public Storage putStorage(Long facilityId, Long storageId, Storage storage);

    public Storage patchStorage(Long storageId, Storage storage);

    public void deleteStorage(Long storageId);

    public boolean storageExists(Long storageId);

}
