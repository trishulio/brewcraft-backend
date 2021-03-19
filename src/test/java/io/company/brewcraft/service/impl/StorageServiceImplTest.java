package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.StorageType;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class StorageServiceImplTest {

    private StorageService storageService;

    private StorageRepository storageRepositoryMock;
    
    private FacilityService facilityServiceMock;
        
    @BeforeEach
    public void init() {
        storageRepositoryMock = mock(StorageRepository.class);
        facilityServiceMock = mock(FacilityService.class);
        storageService = new StorageServiceImpl(storageRepositoryMock, facilityServiceMock);
    }

    @Test
    public void testGetStorages_returnsStorages() throws Exception {
        Storage storageEntity = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
                
        List<Storage> storageList = Arrays.asList(storageEntity);
        
        Page<Storage> expectedStorages = new PageImpl<>(storageList);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(storageRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedStorages);

        Page<Storage> actualStorages = storageService.getAllStorages(0, 100, Set.of("id"), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(1, actualStorages.getNumberOfElements());
        
        Storage actualStorage = actualStorages.get().findFirst().get();

        assertEquals(storageEntity.getId(), actualStorage.getId());
        assertEquals(storageEntity.getName(), actualStorage.getName());
        assertEquals(storageEntity.getType(), actualStorage.getType());
        assertEquals(storageEntity.getFacility().getId(), actualStorage.getFacility().getId());
        assertEquals(storageEntity.getLastUpdated(), actualStorage.getLastUpdated());
        assertEquals(storageEntity.getCreatedAt(), actualStorage.getCreatedAt());
        assertEquals(storageEntity.getVersion(), actualStorage.getVersion());      
    }
    
    @Test
    public void testGetStorage_returnsStorage() throws Exception {
        Long id = 1L;
        Storage storageEntity = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<Storage> expectedStorageEntity = Optional.ofNullable(storageEntity);

        when(storageRepositoryMock.findById(id)).thenReturn(expectedStorageEntity);

        Storage returnedStorage = storageService.getStorage(id);

        assertEquals(expectedStorageEntity.get().getId(), returnedStorage.getId());
        assertEquals(expectedStorageEntity.get().getName(), returnedStorage.getName());
        assertEquals(expectedStorageEntity.get().getType(), returnedStorage.getType());
        assertEquals(expectedStorageEntity.get().getFacility().getId(), returnedStorage.getFacility().getId());
        assertEquals(expectedStorageEntity.get().getLastUpdated(), returnedStorage.getLastUpdated());
        assertEquals(expectedStorageEntity.get().getCreatedAt(), returnedStorage.getCreatedAt());
        assertEquals(expectedStorageEntity.get().getVersion(), returnedStorage.getVersion());  
    }

    @Test
    public void testAddStorage_Success() throws Exception {
        Long facilityId = 2L;
        
        Storage storage = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        Storage storageEntity = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Storage> persistedStorageCaptor = ArgumentCaptor.forClass(Storage.class);
        
        when(facilityServiceMock.getFacility(facilityId)).thenReturn(new Facility(2L, null, null, null, null, null, null, null, null, null));
        
        when(storageRepositoryMock.saveAndFlush(persistedStorageCaptor.capture())).thenReturn(storageEntity);

        Storage returnedStorage = storageService.addStorage(facilityId, storage);
        
        //Assert persisted entity
        assertEquals(storage.getId(), persistedStorageCaptor.getValue().getId());
        assertEquals(storage.getName(), persistedStorageCaptor.getValue().getName());
        assertEquals(storage.getType(), persistedStorageCaptor.getValue().getType());
        assertEquals(storage.getFacility().getId(), persistedStorageCaptor.getValue().getFacility().getId());
        assertEquals(storage.getLastUpdated(), persistedStorageCaptor.getValue().getLastUpdated());
        assertEquals(storage.getCreatedAt(), persistedStorageCaptor.getValue().getCreatedAt());
        assertEquals(storage.getVersion(), persistedStorageCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(storageEntity.getId(), returnedStorage.getId());
        assertEquals(storageEntity.getName(), returnedStorage.getName());
        assertEquals(storageEntity.getType(), returnedStorage.getType());
        assertEquals(storageEntity.getFacility().getId(), returnedStorage.getFacility().getId());
        assertEquals(storageEntity.getLastUpdated(), returnedStorage.getLastUpdated());
        assertEquals(storageEntity.getCreatedAt(), returnedStorage.getCreatedAt());
        assertEquals(storageEntity.getVersion(), returnedStorage.getVersion());  
    }
    
    @Test
    public void testAddStorage_throwsWhenFacilityDoesNotExist() throws Exception {
        Long facilityId = 2L;
        
        Storage storage = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(facilityServiceMock.getFacility(facilityId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            storageService.addStorage(facilityId, storage);
            verify(storageRepositoryMock, times(0)).saveAndFlush(Mockito.any(Storage.class));
        });
    }
    
    @Test
    public void testPutStorage_success() throws Exception {
        Long facilityId = 2L;
        Long id = 1L;

        Storage putStorage = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Storage storageEntity = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Storage> persistedStorageCaptor = ArgumentCaptor.forClass(Storage.class);

        when(facilityServiceMock.getFacility(facilityId)).thenReturn(new Facility(2L, null, null, null, null, null, null, null, null, null));

        when(storageRepositoryMock.saveAndFlush(persistedStorageCaptor.capture())).thenReturn(storageEntity);

        Storage returnedStorage = storageService.putStorage(facilityId, id, putStorage);
       
        //Assert persisted entity
        assertEquals(putStorage.getId(), persistedStorageCaptor.getValue().getId());
        assertEquals(putStorage.getName(), persistedStorageCaptor.getValue().getName());
        assertEquals(putStorage.getType(), persistedStorageCaptor.getValue().getType());
        assertEquals(putStorage.getFacility().getId(), persistedStorageCaptor.getValue().getFacility().getId());
        //assertEquals(null, persistedStorageCaptor.getValue().getLastUpdated());
        //assertEquals(putStorage.getCreatedAt(), persistedStorageCaptor.getValue().getCreatedAt());
        assertEquals(putStorage.getVersion(), persistedStorageCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(storageEntity.getId(), returnedStorage.getId());
        assertEquals(storageEntity.getName(), returnedStorage.getName());
        assertEquals(storageEntity.getType(), returnedStorage.getType());
        assertEquals(storageEntity.getFacility().getId(), returnedStorage.getFacility().getId());
        assertEquals(storageEntity.getLastUpdated(), returnedStorage.getLastUpdated());
        assertEquals(storageEntity.getCreatedAt(), returnedStorage.getCreatedAt());
        assertEquals(storageEntity.getVersion(), returnedStorage.getVersion());  
    }
     
    @Test
    public void testPutStorage_throwsIfFacilityDoesNotExist() throws Exception {
        Long id = 1L;
        Long facilityId = 2L;

        Storage putStorage = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(storageRepositoryMock.findById(facilityId)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            storageService.putStorage(facilityId, id, putStorage);
            verify(storageRepositoryMock, times(0)).saveAndFlush(Mockito.any(Storage.class));
        });
    }
    
    @Test
    public void testPatchStorage_success() throws Exception {
        Long facilityId = 2L;
        Long id = 1L;
        Storage patchedStorage = new Storage(1L, null, "updatedName", null, null, null, null);
        Storage existingStorageEntity = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "testName1", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Storage persistedStorageEntity = new Storage(1L, new Facility(2L, null, null, null, null, null, null, null, null, null), "updatedName", StorageType.GENERAL, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Storage> persistedStorageCaptor = ArgumentCaptor.forClass(Storage.class);

        when(storageRepositoryMock.findById(id)).thenReturn(Optional.of(existingStorageEntity));

        when(facilityServiceMock.getFacility(facilityId)).thenReturn(new Facility(2L, null, null, null, null, null, null, null, null, null));
        
        when(storageRepositoryMock.saveAndFlush(persistedStorageCaptor.capture())).thenReturn(persistedStorageEntity);

        Storage returnedStorage = storageService.patchStorage(id, patchedStorage);
       
        //Assert persisted entity
        assertEquals(existingStorageEntity.getId(), persistedStorageCaptor.getValue().getId());
        assertEquals(patchedStorage.getName(), persistedStorageCaptor.getValue().getName());
        assertEquals(existingStorageEntity.getType(), persistedStorageCaptor.getValue().getType());
        assertEquals(existingStorageEntity.getFacility().getId(), persistedStorageCaptor.getValue().getFacility().getId());
        assertEquals(existingStorageEntity.getLastUpdated(), persistedStorageCaptor.getValue().getLastUpdated());
        //assertEquals(existingStorageEntity.getCreatedAt(), persistedStorageCaptor.getValue().getCreatedAt());
        assertEquals(existingStorageEntity.getVersion(), persistedStorageCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedStorageEntity.getId(), returnedStorage.getId());
        assertEquals(persistedStorageEntity.getName(), returnedStorage.getName());
        assertEquals(persistedStorageEntity.getType(), returnedStorage.getType());
        assertEquals(persistedStorageEntity.getFacility().getId(), returnedStorage.getFacility().getId());
        assertEquals(persistedStorageEntity.getLastUpdated(), returnedStorage.getLastUpdated());
        assertEquals(persistedStorageEntity.getCreatedAt(), returnedStorage.getCreatedAt());
        assertEquals(persistedStorageEntity.getVersion(), returnedStorage.getVersion());  
    }
    
    @Test
    public void testPatchStorage_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Storage storage = new Storage();
        
        when(storageRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            storageService.patchStorage(id, storage);
            verify(storageRepositoryMock, times(0)).saveAndFlush(Mockito.any(Storage.class));
        });
    }
    @Test
    public void testDeleteStorage_success() throws Exception {
        Long id = 1L;
        storageService.deleteStorage(id);
        
        verify(storageRepositoryMock, times(1)).deleteById(id);
    }
    
    @Test
    public void testStorageService_classIsTransactional() throws Exception {
        Transactional transactional = storageService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testStorageService_methodsAreNotTransactional() throws Exception {
        Method[] methods = storageService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
