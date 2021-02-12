package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.StorageEntity;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.repository.StorageRepository;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class StorageServiceImplTest {

    private StorageService storageService;

    private StorageRepository storageRepositoryMock;
    
    private FacilityRepository facilityRepositoryMock;
        
    @BeforeEach
    public void init() {
        storageRepositoryMock = mock(StorageRepository.class);
        facilityRepositoryMock = mock(FacilityRepository.class);
        storageService = new StorageServiceImpl(storageRepositoryMock, facilityRepositoryMock);
    }

    @Test
    public void testGetAllStorages_returnsStorages() throws Exception {
        StorageEntity storage1 = new StorageEntity();
        StorageEntity storage2 = new StorageEntity();
                
        List<StorageEntity> storages = Arrays.asList(storage1, storage2);
        
        Page<StorageEntity> expectedStorages = new PageImpl<>(storages);

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(storageRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedStorages);

        Page<StorageEntity> actualStorages = storageService.getAllStorages(0, 100, new HashSet<>(Arrays.asList("id")), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(expectedStorages, actualStorages);
    }
    
    @Test
    public void testGetStorage_returnsStorage() throws Exception {
        Long id = 1L;
        Optional<StorageEntity> expectedStorage = Optional.ofNullable(new StorageEntity());

        when(storageRepositoryMock.findById(id)).thenReturn(expectedStorage);

        StorageEntity actualStorage = storageService.getStorage(id);

        assertSame(expectedStorage.get(), actualStorage);
    }

    @Test
    public void testAddStorage_SavesStorage() throws Exception {
        Long facilityId = 1L;
        FacilityEntity facility = new FacilityEntity();
        StorageEntity storageMock = Mockito.mock(StorageEntity.class);
        
        when(facilityRepositoryMock.findById(facilityId)).thenReturn(Optional.of(facility));
        
        storageService.addStorage(facilityId, storageMock);
        
        verify(storageMock, times(1)).setFacility(facility);
        verify(storageRepositoryMock, times(1)).save(storageMock);
    }
    
    @Test
    public void testAddStorage_throwsEntityNotFoundException() throws Exception {
        StorageEntity storage = new StorageEntity();
        
        when(facilityRepositoryMock.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> {
            storageService.addStorage(1L, storage);
            verify(facilityRepositoryMock, times(0)).save(Mockito.any(FacilityEntity.class));
        });
    }
    
    @Test
    public void testPutStorage_doesNotOuterJoinWhenThereIsNoExistingStorage() throws Exception {
        Long facilityId = 1L;
        FacilityEntity facility = new FacilityEntity();
        Long storageId = 1L;
        StorageEntity storage = mock(StorageEntity.class);
        
        when(storageRepositoryMock.findById(storageId)).thenReturn(Optional.empty());
        when(facilityRepositoryMock.findById(facilityId)).thenReturn(Optional.of(facility));
                
        storageService.putStorage(facilityId, storageId, storage);
       
        verify(storage, times(0)).outerJoin(Mockito.any(StorageEntity.class));
        verify(storage, times(1)).setId(storageId);
        verify(storageRepositoryMock, times(1)).save(storage);
    }
    
    @Test
    public void testPutStorage_doesOuterJoinWhenThereIsAnExistingStorage() throws Exception {
        Long facilityId = 1L;
        FacilityEntity facility = new FacilityEntity();
        Long storageId = 1L;
        StorageEntity storage = mock(StorageEntity.class);
        StorageEntity existingStorage = new StorageEntity(storageId, new FacilityEntity(), null, null, null, null, null);
        
        when(storageRepositoryMock.findById(storageId)).thenReturn(Optional.of(existingStorage));
        when(facilityRepositoryMock.findById(facilityId)).thenReturn(Optional.of(facility));
                
        storageService.putStorage(facilityId, storageId, storage);
       
        verify(storage, times(1)).outerJoin(existingStorage);
        verify(storage, times(1)).setId(storageId);
        verify(storageRepositoryMock, times(1)).save(storage);
    }
    
    @Test
    public void testPatchStorage_success() throws Exception {
        Long id = 1L;
        StorageEntity updatedStorageMock = mock(StorageEntity.class);
        StorageEntity existingStorage = new StorageEntity(id, new FacilityEntity(), null, null, null, null, null);
        
        when(storageRepositoryMock.findById(id)).thenReturn(Optional.of(existingStorage));
 
        storageService.patchStorage(id, updatedStorageMock);
       
        verify(updatedStorageMock, times(1)).outerJoin(existingStorage);
        verify(storageRepositoryMock, times(1)).save(updatedStorageMock);
    }
    
    @Test
    public void testPatchStorage_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        StorageEntity storage = new StorageEntity();
        
        when(storageRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            storageService.patchStorage(id, storage);
            verify(storageRepositoryMock, times(0)).save(Mockito.any(StorageEntity.class));
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
