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

import io.company.brewcraft.model.Facility;
import io.company.brewcraft.model.Storage;
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
        Storage storage1 = new Storage();
        Storage storage2 = new Storage();
                
        List<Storage> storages = Arrays.asList(storage1, storage2);
        
        Page<Storage> expectedStorages = new PageImpl<>(storages);

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(storageRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedStorages);

        Page<Storage> actualStorages = storageService.getAllStorages(0, 100, new String[]{"id"}, true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(expectedStorages, actualStorages);
    }
    
    @Test
    public void testGetStorage_returnsStorage() throws Exception {
        Long id = 1L;
        Optional<Storage> expectedStorage = Optional.ofNullable(new Storage());

        when(storageRepositoryMock.findById(id)).thenReturn(expectedStorage);

        Storage actualStorage = storageService.getStorage(id);

        assertSame(expectedStorage.get(), actualStorage);
    }

    @Test
    public void testAddStorage_SavesStorage() throws Exception {
        Storage storage = new Storage();
        Facility facilityMock = mock(Facility.class);
        Optional<Facility> facilityMockOpt = Optional.ofNullable(facilityMock);
        
        when(facilityRepositoryMock.findById(1L)).thenReturn(facilityMockOpt);

        storageService.addStorage(1L, storage);
        
        verify(facilityMock, times(1)).addStorage(storage);
        verify(facilityRepositoryMock, times(1)).save(facilityMock);
    }
    
    @Test
    public void testAddStorage_throwsEntityNotFoundException() throws Exception {
        Storage storage = new Storage();
        
        when(facilityRepositoryMock.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> {
            storageService.addStorage(1L, storage);
            verify(facilityRepositoryMock, times(0)).save(Mockito.any(Facility.class));
        });
    }
    
    @Test
    public void testUpdateStorage_success() throws Exception {
        Long id = 1L;
        Optional<Storage> storage = Optional.ofNullable(new Storage(id, new Facility(), "Storage 1", null, null, null, null));
        Storage updatedStorage = new Storage(id, null, "Storage new 1", null, null, null, null);

        when(storageRepositoryMock.findById(id)).thenReturn(storage);
                
        storageService.putStorage(id, storage.get());
       
        ArgumentCaptor<Storage> storageArgument = ArgumentCaptor.forClass(Storage.class);
        verify(storageRepositoryMock, times(1)).save(updatedStorage);
        verify(storageRepositoryMock, times(1)).save(storageArgument.capture());
        assertSame(id, storageArgument.getValue().getId());
        assertSame(storage.get().getFacility(), storageArgument.getValue().getFacility());
    }
    
    @Test
    public void testUpdateStorage_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Storage storage = new Storage();
        
        when(storageRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            storageService.putStorage(id, storage);
            verify(storageRepositoryMock, times(0)).save(Mockito.any(Storage.class));
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
