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

import io.company.brewcraft.model.FacilityEntity;
import io.company.brewcraft.model.StorageEntity;
import io.company.brewcraft.pojo.Address;
import io.company.brewcraft.pojo.Equipment;
import io.company.brewcraft.pojo.Facility;
import io.company.brewcraft.pojo.Storage;
import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.model.FacilityAddressEntity;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class FacilityServiceImplTest {

    private FacilityService facilityService;

    private FacilityRepository facilityRepositoryMock;

    @BeforeEach
    public void init() {
        facilityRepositoryMock = mock(FacilityRepository.class);
        
        facilityService = new FacilityServiceImpl(facilityRepositoryMock);
    }

    @Test
    public void testGetAllFacilities_returnsFacilities() throws Exception {
        FacilityEntity facility = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", List.of(new EquipmentEntity(2L, null, null, null, null, null, null, null, null, null)), List.of(new StorageEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        List<FacilityEntity> facilityList = Arrays.asList(facility);
        
        Page<FacilityEntity> expectedFacilities = new PageImpl<>(facilityList);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(facilityRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedFacilities);

        Page<Facility> actualSuppliers = facilityService.getAllFacilities(0, 100, Set.of("id"), true);

        assertSame(0, pageableArgument.getValue().getPageNumber());
        assertSame(100, pageableArgument.getValue().getPageSize());
        assertSame(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertSame("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertSame(1, actualSuppliers.getNumberOfElements());
        
        Facility actualSupplier = actualSuppliers.get().findFirst().get();
        
        assertSame(facility.getId(), actualSupplier.getId());
        assertSame(facility.getName(), actualSupplier.getName());
        assertSame(facility.getAddress().getId(), actualSupplier.getAddress().getId());
        assertSame(facility.getEquipment().size(), actualSupplier.getEquipment().size());
        assertSame(facility.getEquipment().get(0).getId(), actualSupplier.getEquipment().get(0).getId());
        assertSame(facility.getStorages().size(), actualSupplier.getStorages().size());
        assertSame(facility.getStorages().get(0).getId(), actualSupplier.getStorages().get(0).getId());
        assertSame(facility.getPhoneNumber(), actualSupplier.getPhoneNumber());
        assertSame(facility.getFaxNumber(), actualSupplier.getFaxNumber());
        assertSame(facility.getLastUpdated(), actualSupplier.getLastUpdated());
        assertSame(facility.getCreatedAt(), actualSupplier.getCreatedAt());
        assertSame(facility.getVersion(), actualSupplier.getVersion());
    }
    
    @Test
    public void testGetFacility_returnsFacility() throws Exception {
        Long id = 1L;
        FacilityEntity facility = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", List.of(new EquipmentEntity(2L, null, null, null, null, null, null, null, null, null)), List.of(new StorageEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        Optional<FacilityEntity> expectedFacilityEntity = Optional.ofNullable(facility);

        when(facilityRepositoryMock.findById(id)).thenReturn(expectedFacilityEntity);

        Facility actualSupplier = facilityService.getFacility(id);

        assertEquals(expectedFacilityEntity.get().getId(), actualSupplier.getId());
        assertEquals(expectedFacilityEntity.get().getName(), actualSupplier.getName());
        assertEquals(expectedFacilityEntity.get().getAddress().getId(), actualSupplier.getAddress().getId());
        assertEquals(expectedFacilityEntity.get().getEquipment().size(), actualSupplier.getEquipment().size());
        assertEquals(expectedFacilityEntity.get().getEquipment().get(0).getId(), actualSupplier.getEquipment().get(0).getId());
        assertEquals(expectedFacilityEntity.get().getStorages().size(), actualSupplier.getStorages().size());
        assertEquals(expectedFacilityEntity.get().getStorages().get(0).getId(), actualSupplier.getStorages().get(0).getId());
        assertEquals(expectedFacilityEntity.get().getPhoneNumber(), actualSupplier.getPhoneNumber());
        assertEquals(expectedFacilityEntity.get().getFaxNumber(), actualSupplier.getFaxNumber());
        assertEquals(expectedFacilityEntity.get().getLastUpdated(), actualSupplier.getLastUpdated());
        assertEquals(expectedFacilityEntity.get().getCreatedAt(), actualSupplier.getCreatedAt());
        assertEquals(expectedFacilityEntity.get().getVersion(), actualSupplier.getVersion());
    }

    @Test
    public void testAddFacility_SavesFacility() throws Exception {
        Facility facility = new Facility(1L, "facility1", new Address(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode"), "6045555555", "6045555555", List.of(new Equipment(2L, null, null, null, null, null, null, null, null, null)), List.of(new Storage(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        FacilityEntity facilityEntity = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", List.of(new EquipmentEntity(2L, null, null, null, null, null, null, null, null, null)), List.of(new StorageEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<FacilityEntity> persistedFacilityCaptor = ArgumentCaptor.forClass(FacilityEntity.class);

        when(facilityRepositoryMock.save(persistedFacilityCaptor.capture())).thenReturn(facilityEntity);

        Facility returnedFacility = facilityService.addFacility(facility);
        
        //Assert persisted entity
        assertEquals(facility.getId(), persistedFacilityCaptor.getValue().getId());
        assertEquals(facility.getName(), persistedFacilityCaptor.getValue().getName());
        assertEquals(facility.getAddress().getId(), persistedFacilityCaptor.getValue().getAddress().getId());
        assertEquals(facility.getEquipment().size(), persistedFacilityCaptor.getValue().getEquipment().size());
        assertEquals(facility.getEquipment().get(0).getId(), persistedFacilityCaptor.getValue().getEquipment().get(0).getId());
        assertEquals(facility.getStorages().size(), persistedFacilityCaptor.getValue().getStorages().size());
        assertEquals(facility.getStorages().get(0).getId(), persistedFacilityCaptor.getValue().getStorages().get(0).getId());
        assertEquals(facility.getPhoneNumber(), persistedFacilityCaptor.getValue().getPhoneNumber());
        assertEquals(facility.getFaxNumber(), persistedFacilityCaptor.getValue().getFaxNumber());
        assertEquals(facility.getLastUpdated(), persistedFacilityCaptor.getValue().getLastUpdated());
        assertEquals(facility.getCreatedAt(), persistedFacilityCaptor.getValue().getCreatedAt());
        assertEquals(facility.getVersion(), persistedFacilityCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(facilityEntity.getId(), returnedFacility.getId());
        assertEquals(facilityEntity.getName(), returnedFacility.getName());
        assertEquals(facilityEntity.getAddress().getId(), returnedFacility.getAddress().getId());
        assertEquals(facilityEntity.getEquipment().size(), returnedFacility.getEquipment().size());
        assertEquals(facilityEntity.getEquipment().get(0).getId(), returnedFacility.getEquipment().get(0).getId());
        assertEquals(facilityEntity.getStorages().size(), returnedFacility.getStorages().size());
        assertEquals(facilityEntity.getStorages().get(0).getId(), returnedFacility.getStorages().get(0).getId());
        assertEquals(facilityEntity.getPhoneNumber(), returnedFacility.getPhoneNumber());
        assertEquals(facilityEntity.getFaxNumber(), returnedFacility.getFaxNumber());
        assertEquals(facilityEntity.getLastUpdated(), returnedFacility.getLastUpdated());
        assertEquals(facilityEntity.getCreatedAt(), returnedFacility.getCreatedAt());
        assertEquals(facilityEntity.getVersion(), returnedFacility.getVersion()); 
    }
    
    @Test
    public void testPutFacility_Success() throws Exception {
        Long id = 1L;
        
        Facility putFacility = new Facility(1L, "facility1", new Address(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode"), "6045555555", "6045555555", List.of(new Equipment(2L, null, null, null, null, null, null, null, null, null)), List.of(new Storage(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        FacilityEntity facilityEntity = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", List.of(new EquipmentEntity(2L, null, null, null, null, null, null, null, null, null)), List.of(new StorageEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<FacilityEntity> persistedFacilityCaptor = ArgumentCaptor.forClass(FacilityEntity.class);

        when(facilityRepositoryMock.save(persistedFacilityCaptor.capture())).thenReturn(facilityEntity);

        Facility returnedFacility = facilityService.putFacility(id, putFacility);
       
        //Assert persisted entity
        assertEquals(putFacility.getId(), persistedFacilityCaptor.getValue().getId());
        assertEquals(putFacility.getName(), persistedFacilityCaptor.getValue().getName());
        assertEquals(putFacility.getAddress().getId(), persistedFacilityCaptor.getValue().getAddress().getId());
        assertEquals(putFacility.getEquipment().size(), persistedFacilityCaptor.getValue().getEquipment().size());
        assertEquals(putFacility.getEquipment().get(0).getId(), persistedFacilityCaptor.getValue().getEquipment().get(0).getId());
        assertEquals(putFacility.getStorages().size(), persistedFacilityCaptor.getValue().getStorages().size());
        assertEquals(putFacility.getStorages().get(0).getId(), persistedFacilityCaptor.getValue().getStorages().get(0).getId());
        assertEquals(putFacility.getPhoneNumber(), persistedFacilityCaptor.getValue().getPhoneNumber());
        assertEquals(putFacility.getFaxNumber(), persistedFacilityCaptor.getValue().getFaxNumber());
        assertEquals(null, persistedFacilityCaptor.getValue().getLastUpdated());
        //assertEquals(putFacility.getCreatedAt(), persistedFacilityCaptor.getValue().getCreatedAt());
        assertEquals(putFacility.getVersion(), persistedFacilityCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(facilityEntity.getId(), returnedFacility.getId());
        assertEquals(facilityEntity.getName(), returnedFacility.getName());
        assertEquals(facilityEntity.getAddress().getId(), returnedFacility.getAddress().getId());
        assertEquals(facilityEntity.getEquipment().size(), returnedFacility.getEquipment().size());
        assertEquals(facilityEntity.getEquipment().get(0).getId(), returnedFacility.getEquipment().get(0).getId());
        assertEquals(facilityEntity.getStorages().size(), returnedFacility.getStorages().size());
        assertEquals(facilityEntity.getStorages().get(0).getId(), returnedFacility.getStorages().get(0).getId());
        assertEquals(facilityEntity.getPhoneNumber(), returnedFacility.getPhoneNumber());
        assertEquals(facilityEntity.getFaxNumber(), returnedFacility.getFaxNumber());
        assertEquals(facilityEntity.getLastUpdated(), returnedFacility.getLastUpdated());
        assertEquals(facilityEntity.getCreatedAt(), returnedFacility.getCreatedAt());
        assertEquals(facilityEntity.getVersion(), returnedFacility.getVersion()); 
    }
    
    @Test
    public void testPatchFacility_success() throws Exception {
        Long id = 1L;
        
        Facility patchedFacility = new Facility(1L, "updatedName", null, null, null, null, null, null, null, null);
        FacilityEntity existingFacility = new FacilityEntity(1L, "facility1", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", List.of(new EquipmentEntity(2L, null, null, null, null, null, null, null, null, null)), List.of(new StorageEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        FacilityEntity persistedFacilityEntity = new FacilityEntity(1L, "updatedName", new FacilityAddressEntity(1L, "addressLine1", "addressLine2", "country", "province", "city", "postalCode", null, null), "6045555555", "6045555555", List.of(new EquipmentEntity(2L, null, null, null, null, null, null, null, null, null)), List.of(new StorageEntity(3L, null, null, null, null, null, null)), LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<FacilityEntity> persistedFacilityCaptor = ArgumentCaptor.forClass(FacilityEntity.class);

        when(facilityRepositoryMock.findById(id)).thenReturn(Optional.of(existingFacility));
 
        when(facilityRepositoryMock.save(persistedFacilityCaptor.capture())).thenReturn(persistedFacilityEntity);

        Facility returnedFacility = facilityService.patchFacility(id, patchedFacility);
       
        //Assert persisted entity
        assertEquals(existingFacility.getId(), persistedFacilityCaptor.getValue().getId());
        assertEquals(patchedFacility.getName(), persistedFacilityCaptor.getValue().getName());
        assertEquals(existingFacility.getAddress().getId(), persistedFacilityCaptor.getValue().getAddress().getId());
        assertEquals(existingFacility.getEquipment().size(), persistedFacilityCaptor.getValue().getEquipment().size());
        assertEquals(existingFacility.getEquipment().get(0).getId(), persistedFacilityCaptor.getValue().getEquipment().get(0).getId());
        assertEquals(existingFacility.getStorages().size(), persistedFacilityCaptor.getValue().getStorages().size());
        assertEquals(existingFacility.getStorages().get(0).getId(), persistedFacilityCaptor.getValue().getStorages().get(0).getId());
        assertEquals(existingFacility.getPhoneNumber(), persistedFacilityCaptor.getValue().getPhoneNumber());
        assertEquals(existingFacility.getFaxNumber(), persistedFacilityCaptor.getValue().getFaxNumber());
        assertEquals(existingFacility.getLastUpdated(), persistedFacilityCaptor.getValue().getLastUpdated());
        assertEquals(existingFacility.getCreatedAt(), persistedFacilityCaptor.getValue().getCreatedAt());
        assertEquals(existingFacility.getVersion(), persistedFacilityCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedFacilityEntity.getId(), returnedFacility.getId());
        assertEquals(persistedFacilityEntity.getName(), returnedFacility.getName());
        assertEquals(persistedFacilityEntity.getAddress().getId(), returnedFacility.getAddress().getId());
        assertEquals(persistedFacilityEntity.getEquipment().size(), returnedFacility.getEquipment().size());
        assertEquals(persistedFacilityEntity.getEquipment().get(0).getId(), returnedFacility.getEquipment().get(0).getId());
        assertEquals(persistedFacilityEntity.getStorages().size(), returnedFacility.getStorages().size());
        assertEquals(persistedFacilityEntity.getStorages().get(0).getId(), returnedFacility.getStorages().get(0).getId());
        assertEquals(persistedFacilityEntity.getPhoneNumber(), returnedFacility.getPhoneNumber());
        assertEquals(persistedFacilityEntity.getFaxNumber(), returnedFacility.getFaxNumber());
        assertEquals(persistedFacilityEntity.getLastUpdated(), returnedFacility.getLastUpdated());
        assertEquals(persistedFacilityEntity.getCreatedAt(), returnedFacility.getCreatedAt());
        assertEquals(persistedFacilityEntity.getVersion(), returnedFacility.getVersion()); 
    }
    
    @Test
    public void testPatchFacility_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Facility facility = new Facility();
        
        when(facilityRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            facilityService.patchFacility(id, facility);
            verify(facilityRepositoryMock, times(0)).save(Mockito.any(FacilityEntity.class));
        });
    }

    @Test
    public void testDeleteFacility_success() throws Exception {
        Long id = 1L;
        facilityService.deleteFacility(id);
        
        verify(facilityRepositoryMock, times(1)).deleteById(id);
    }
    
    @Test
    public void testFacilityExists_success() throws Exception {
        Long id = 1L;
        facilityService.facilityExists(id);
        
        verify(facilityRepositoryMock, times(1)).existsById(id);
    }
    
    @Test
    public void testFacilityService_classIsTransactional() throws Exception {
        Transactional transactional = facilityService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testFacilityService_methodsAreNotTransactional() throws Exception {
        Method[] methods = facilityService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
