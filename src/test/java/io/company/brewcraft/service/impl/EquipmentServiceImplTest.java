package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.utils.SupportedUnits;

public class EquipmentServiceImplTest {

    private EquipmentService equipmentService;

    private EquipmentRepository equipmentRepositoryMock;
    
    private FacilityService facilityServiceMock;
            
    @BeforeEach
    public void init() {
        equipmentRepositoryMock = mock(EquipmentRepository.class);
        facilityServiceMock = mock(FacilityService.class);

        equipmentService = new EquipmentServiceImpl(equipmentRepositoryMock, facilityServiceMock);
    }

    @Test
    public void testGetAllEquipment_returnsEquipment() throws Exception {
        Equipment equipmentEntity = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE, BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        List<Equipment> equipmentList = Arrays.asList(equipmentEntity);
        
        Page<Equipment> expectedEquipmentEntity = new PageImpl<>(equipmentList);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(equipmentRepositoryMock.findAll(ArgumentMatchers.<Specification<Equipment>>any(),pageableArgument.capture())).thenReturn(expectedEquipmentEntity);

        Page<Equipment> actualEquipments = equipmentService.getAllEquipment(null, null, null, null, 0, 100, new TreeSet<>(List.of("id")), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(1, actualEquipments.getNumberOfElements());
        
        Equipment actualEquipment = actualEquipments.get().findFirst().get();

        assertEquals(equipmentEntity.getId(), actualEquipment.getId());
        assertEquals(equipmentEntity.getName(), actualEquipment.getName());
        assertEquals(equipmentEntity.getStatus(), actualEquipment.getStatus());
        assertEquals(equipmentEntity.getType(), actualEquipment.getType());
        assertEquals(equipmentEntity.getMaxCapacity().getUnit().getSymbol(), actualEquipment.getMaxCapacity().getUnit().getSymbol());
        assertEquals(equipmentEntity.getMaxCapacity().getValue(), actualEquipment.getMaxCapacity().getValue());
        assertEquals(equipmentEntity.getMaxCapacityDisplayUnit().getSymbol(), actualEquipment.getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(equipmentEntity.getFacility().getId(), actualEquipment.getFacility().getId());
        assertEquals(equipmentEntity.getLastUpdated(), actualEquipment.getLastUpdated());
        assertEquals(equipmentEntity.getCreatedAt(), actualEquipment.getCreatedAt());
        assertEquals(equipmentEntity.getVersion(), actualEquipment.getVersion());      
    }
    
    @Test
    public void testGetEquipment_returnsEquipment() throws Exception {
        Long id = 1L;
        Equipment equipmentEntity = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Optional<Equipment> expectedEquipmentEntity = Optional.ofNullable(equipmentEntity);

        when(equipmentRepositoryMock.findById(id)).thenReturn(expectedEquipmentEntity);

        Equipment returnedEntity = equipmentService.getEquipment(id);

        assertEquals(expectedEquipmentEntity.get().getId(), returnedEntity.getId());
        assertEquals(expectedEquipmentEntity.get().getName(), returnedEntity.getName());
        assertEquals(expectedEquipmentEntity.get().getStatus(), returnedEntity.getStatus());
        assertEquals(expectedEquipmentEntity.get().getType(), returnedEntity.getType());
        assertEquals(expectedEquipmentEntity.get().getMaxCapacity().getUnit().getSymbol(), returnedEntity.getMaxCapacity().getUnit().getSymbol());
        assertEquals(expectedEquipmentEntity.get().getMaxCapacity().getValue(), returnedEntity.getMaxCapacity().getValue());
        assertEquals(expectedEquipmentEntity.get().getMaxCapacityDisplayUnit().getSymbol(), returnedEntity.getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(expectedEquipmentEntity.get().getFacility().getId(), returnedEntity.getFacility().getId());
        assertEquals(expectedEquipmentEntity.get().getLastUpdated(), returnedEntity.getLastUpdated());
        assertEquals(expectedEquipmentEntity.get().getCreatedAt(), returnedEntity.getCreatedAt());
        assertEquals(expectedEquipmentEntity.get().getVersion(), returnedEntity.getVersion());  
    }

    @Test
    public void testAddEquipment_Success() throws Exception {
        Long facilityId = 2L;
                
        Equipment equipment = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        Equipment equipmentEntity = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Equipment> persistedEquipmentCaptor = ArgumentCaptor.forClass(Equipment.class);
        
        when(facilityServiceMock.getFacility(facilityId)).thenReturn(new Facility(2L));
        
        when(equipmentRepositoryMock.saveAndFlush(persistedEquipmentCaptor.capture())).thenReturn(equipmentEntity);

        Equipment returnedEquipment = equipmentService.addEquipment(facilityId, equipment);
        
        //Assert persisted entity
        assertEquals(equipment.getId(), persistedEquipmentCaptor.getValue().getId());
        assertEquals(equipment.getName(), persistedEquipmentCaptor.getValue().getName());
        assertEquals(equipment.getStatus(), persistedEquipmentCaptor.getValue().getStatus());
        assertEquals(equipment.getType(), persistedEquipmentCaptor.getValue().getType());
        assertEquals(equipmentEntity.getMaxCapacity().getUnit().getSymbol(), persistedEquipmentCaptor.getValue().getMaxCapacity().getUnit().getSymbol());
        assertEquals(equipment.getMaxCapacity().getValue(), persistedEquipmentCaptor.getValue().getMaxCapacity().getValue());
        assertEquals(equipment.getMaxCapacityDisplayUnit().getSymbol(), persistedEquipmentCaptor.getValue().getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(equipment.getFacility().getId(), persistedEquipmentCaptor.getValue().getFacility().getId());
        assertEquals(equipment.getLastUpdated(), persistedEquipmentCaptor.getValue().getLastUpdated());
        assertEquals(equipment.getCreatedAt(), persistedEquipmentCaptor.getValue().getCreatedAt());
        assertEquals(equipment.getVersion(), persistedEquipmentCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(equipmentEntity.getId(), returnedEquipment.getId());
        assertEquals(equipmentEntity.getName(), returnedEquipment.getName());
        assertEquals(equipmentEntity.getStatus(), returnedEquipment.getStatus());
        assertEquals(equipmentEntity.getType(), returnedEquipment.getType());
        assertEquals(equipmentEntity.getMaxCapacity().getUnit().getSymbol(), returnedEquipment.getMaxCapacity().getUnit().getSymbol());
        assertEquals(equipmentEntity.getMaxCapacity().getValue(), returnedEquipment.getMaxCapacity().getValue());
        assertEquals(equipmentEntity.getMaxCapacityDisplayUnit().getSymbol(), returnedEquipment.getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(equipmentEntity.getFacility().getId(), returnedEquipment.getFacility().getId());
        assertEquals(equipmentEntity.getLastUpdated(), returnedEquipment.getLastUpdated());
        assertEquals(equipmentEntity.getCreatedAt(), returnedEquipment.getCreatedAt());
        assertEquals(equipmentEntity.getVersion(), returnedEquipment.getVersion());  
    }
    
    @Test
    public void testAddEquipment_throwsWhenFacilityDoesNotExist() throws Exception {
        Long facilityId = 2L;
        
        Equipment equipment = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(facilityServiceMock.getFacility(facilityId)).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> {
            equipmentService.addEquipment(facilityId, equipment);
            verify(equipmentRepositoryMock, times(0)).saveAndFlush(Mockito.any(Equipment.class));
        });
    }
    
    @Test
    public void testPutEquipment_success() throws Exception {
        Long facilityId = 2L;
        Long id = 1L;

        Equipment putEquipment = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Equipment equipmentEntity = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Equipment> persistedEquipmentCaptor = ArgumentCaptor.forClass(Equipment.class);

        when(facilityServiceMock.getFacility(facilityId)).thenReturn(new Facility(2L));

        when(equipmentRepositoryMock.saveAndFlush(persistedEquipmentCaptor.capture())).thenReturn(equipmentEntity);

        Equipment returnedEquipment = equipmentService.putEquipment(facilityId, id, putEquipment);
       
        //Assert persisted entity
        assertEquals(putEquipment.getId(), persistedEquipmentCaptor.getValue().getId());
        assertEquals(putEquipment.getName(), persistedEquipmentCaptor.getValue().getName());
        assertEquals(putEquipment.getStatus(), persistedEquipmentCaptor.getValue().getStatus());
        assertEquals(putEquipment.getType(), persistedEquipmentCaptor.getValue().getType());
        assertEquals(putEquipment.getMaxCapacity().getUnit().getSymbol(), persistedEquipmentCaptor.getValue().getMaxCapacity().getUnit().getSymbol());
        assertEquals(putEquipment.getMaxCapacity().getValue(), persistedEquipmentCaptor.getValue().getMaxCapacity().getValue());
        assertEquals(putEquipment.getMaxCapacityDisplayUnit().getSymbol(), persistedEquipmentCaptor.getValue().getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(putEquipment.getFacility().getId(), persistedEquipmentCaptor.getValue().getFacility().getId());
        //assertEquals(null, persistedEquipmentCaptor.getValue().getLastUpdated());
        //assertEquals(putEquipment.getCreatedAt(), persistedEquipmentCaptor.getValue().getCreatedAt());
        assertEquals(putEquipment.getVersion(), persistedEquipmentCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(equipmentEntity.getId(), returnedEquipment.getId());
        assertEquals(equipmentEntity.getName(), returnedEquipment.getName());
        assertEquals(equipmentEntity.getStatus(), returnedEquipment.getStatus());
        assertEquals(equipmentEntity.getType(), returnedEquipment.getType());
        assertEquals(equipmentEntity.getMaxCapacity().getUnit().getSymbol(), returnedEquipment.getMaxCapacity().getUnit().getSymbol());
        assertEquals(equipmentEntity.getMaxCapacity().getValue(), returnedEquipment.getMaxCapacity().getValue());
        assertEquals(equipmentEntity.getMaxCapacityDisplayUnit().getSymbol(), returnedEquipment.getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(equipmentEntity.getFacility().getId(), returnedEquipment.getFacility().getId());
        assertEquals(equipmentEntity.getLastUpdated(), returnedEquipment.getLastUpdated());
        assertEquals(equipmentEntity.getCreatedAt(), returnedEquipment.getCreatedAt());
        assertEquals(equipmentEntity.getVersion(), returnedEquipment.getVersion());  
    }
     
    @Test
    public void testPutEquipment_throwsIfFacilityDoesNotExist() throws Exception {
        Long id = 1L;
        Long facilityId = 2L;

        Equipment putEquipment = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE, BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        
        when(equipmentRepositoryMock.findById(facilityId)).thenReturn(Optional.ofNullable(null));

        assertThrows(EntityNotFoundException.class, () -> {
            equipmentService.putEquipment(facilityId, id, putEquipment);
            verify(equipmentRepositoryMock, times(0)).saveAndFlush(Mockito.any(Equipment.class));
        });
    }
    
    @Test
    public void testPatchEquipment_success() throws Exception {
        Long facilityId = 2L;
        Long id = 1L;
        Equipment patchedEquipment = new Equipment(1L, null, "updatedName", null, null, null, null, null, null, null, null);
        Equipment existingEquipmentEntity = new Equipment(1L, new Facility(2L), "testName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);
        Equipment persistedEquipmentEntity = new Equipment(1L, new Facility(2L), "updatedName", EquipmentType.BARREL, EquipmentStatus.ACTIVE,  BigDecimal.valueOf(100.0), SupportedUnits.LITRE, SupportedUnits.LITRE, LocalDateTime.of(2020, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<Equipment> persistedEquipmentCaptor = ArgumentCaptor.forClass(Equipment.class);

        when(equipmentRepositoryMock.findById(id)).thenReturn(Optional.of(existingEquipmentEntity));

        when(facilityServiceMock.getFacility(facilityId)).thenReturn(new Facility(2L));
        
        when(equipmentRepositoryMock.saveAndFlush(persistedEquipmentCaptor.capture())).thenReturn(persistedEquipmentEntity);

        Equipment returnedEquipment = equipmentService.patchEquipment(id, patchedEquipment);
       
        //Assert persisted entity
        assertEquals(existingEquipmentEntity.getId(), persistedEquipmentCaptor.getValue().getId());
        assertEquals(patchedEquipment.getName(), persistedEquipmentCaptor.getValue().getName());
        assertEquals(existingEquipmentEntity.getStatus(), persistedEquipmentCaptor.getValue().getStatus());
        assertEquals(existingEquipmentEntity.getType(), persistedEquipmentCaptor.getValue().getType());
        assertEquals(existingEquipmentEntity.getMaxCapacity().getUnit().getSymbol(), persistedEquipmentCaptor.getValue().getMaxCapacity().getUnit().getSymbol());
        assertEquals(existingEquipmentEntity.getMaxCapacity().getValue(), persistedEquipmentCaptor.getValue().getMaxCapacity().getValue());
        assertEquals(existingEquipmentEntity.getMaxCapacityDisplayUnit().getSymbol(), persistedEquipmentCaptor.getValue().getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(existingEquipmentEntity.getFacility().getId(), persistedEquipmentCaptor.getValue().getFacility().getId());
        assertEquals(existingEquipmentEntity.getLastUpdated(), persistedEquipmentCaptor.getValue().getLastUpdated());
        //assertEquals(existingEquipmentEntity.getCreatedAt(), persistedEquipmentCaptor.getValue().getCreatedAt());
        assertEquals(existingEquipmentEntity.getVersion(), persistedEquipmentCaptor.getValue().getVersion());
        
        //Assert returned POJO
        assertEquals(persistedEquipmentEntity.getId(), returnedEquipment.getId());
        assertEquals(persistedEquipmentEntity.getName(), returnedEquipment.getName());
        assertEquals(persistedEquipmentEntity.getStatus(), returnedEquipment.getStatus());
        assertEquals(persistedEquipmentEntity.getType(), returnedEquipment.getType());
        assertEquals(persistedEquipmentEntity.getMaxCapacity().getUnit().getSymbol(), returnedEquipment.getMaxCapacity().getUnit().getSymbol());
        assertEquals(persistedEquipmentEntity.getMaxCapacity().getValue(), returnedEquipment.getMaxCapacity().getValue());
        assertEquals(persistedEquipmentEntity.getMaxCapacityDisplayUnit().getSymbol(), returnedEquipment.getMaxCapacityDisplayUnit().getSymbol());
        assertEquals(persistedEquipmentEntity.getFacility().getId(), returnedEquipment.getFacility().getId());
        assertEquals(persistedEquipmentEntity.getLastUpdated(), returnedEquipment.getLastUpdated());
        assertEquals(persistedEquipmentEntity.getCreatedAt(), returnedEquipment.getCreatedAt());
        assertEquals(persistedEquipmentEntity.getVersion(), returnedEquipment.getVersion());  
    }
    
    @Test
    public void testPatchEquipment_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Equipment equipment = new Equipment();
        
        when(equipmentRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            equipmentService.patchEquipment(id, equipment);
            verify(equipmentRepositoryMock, times(0)).saveAndFlush(Mockito.any(Equipment.class));
        });
    }

    @Test
    public void testDeleteEquipment_success() throws Exception {
        Long id = 1L;
        equipmentService.deleteEquipment(id);
        
        verify(equipmentRepositoryMock, times(1)).deleteById(id);
    }
    
    @Test
    public void testEquipmentExists_success() throws Exception {
        Long id = 1L;
        equipmentService.equipmentExists(id);
        
        verify(equipmentRepositoryMock, times(1)).existsById(id);
    }    
       
    @Test
    public void testEquipmentService_classIsTransactional() throws Exception {
        Transactional transactional = equipmentService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testEquipmentService_methodsAreNotTransactional() throws Exception {
        Method[] methods = equipmentService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
