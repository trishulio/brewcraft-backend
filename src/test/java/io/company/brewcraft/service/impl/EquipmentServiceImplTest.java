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

import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.repository.EquipmentRepository;
import io.company.brewcraft.repository.EquipmentRepositoryGetAllEquipmentSpecification;
import io.company.brewcraft.repository.FacilityRepository;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EquipmentServiceImplTest {

    private EquipmentService equipmentService;

    private EquipmentRepository equipmentRepositoryMock;
    
    private FacilityRepository facilityRepositoryMock;
            
    @BeforeEach
    public void init() {
        equipmentRepositoryMock = mock(EquipmentRepository.class);
        facilityRepositoryMock = mock(FacilityRepository.class);

        equipmentService = new EquipmentServiceImpl(equipmentRepositoryMock, facilityRepositoryMock);
    }

    @Test
    public void testGetAllEquipment_returnsEquipment() throws Exception {
        Equipment equipment1 = new Equipment();
        Equipment equipment2 = new Equipment();
                
        List<Equipment> equipment = Arrays.asList(equipment1, equipment2);
        
        Page<Equipment> expectedEquipment = new PageImpl<>(equipment);
        
        ArgumentCaptor<EquipmentRepositoryGetAllEquipmentSpecification> equipmentRepositoryGetAllEquipmentSpecificationArgument = ArgumentCaptor.forClass(EquipmentRepositoryGetAllEquipmentSpecification.class);

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(equipmentRepositoryMock.findAll(equipmentRepositoryGetAllEquipmentSpecificationArgument.capture(), pageableArgument.capture())).thenReturn(expectedEquipment);

        Page<Equipment> actualEquipment = equipmentService.getAllEquipment(null, null, null, null, 0, 100, new HashSet<>(Arrays.asList("id")), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(expectedEquipment, actualEquipment);
    } 
    
    @Test
    public void testGetEquipment_returnsEquipment() throws Exception {
        Long id = 1L;
        Optional<Equipment> expectedEquipment = Optional.ofNullable(new Equipment());

        when(equipmentRepositoryMock.findById(id)).thenReturn(expectedEquipment);

        Equipment actualEquipment = equipmentService.getEquipment(id);

        assertSame(expectedEquipment.get(), actualEquipment);
    }

    @Test
    public void testAddEquipment_savesEquipment() throws Exception {
        Long facilityId = 1L;
        Facility facility = new Facility();
        Equipment equipmentMock = Mockito.mock(Equipment.class);
        
        when(facilityRepositoryMock.findById(facilityId)).thenReturn(Optional.of(facility));
        
        equipmentService.addEquipment(facilityId, equipmentMock);
        
        verify(equipmentMock, times(1)).setFacility(facility);
        verify(equipmentRepositoryMock, times(1)).save(equipmentMock);
    }
    
    @Test
    public void testAddEquipment_throwsWhenNoFacilityExists() throws Exception {
        Long facilityId = 1L;
        Equipment equipmentMock = Mockito.mock(Equipment.class);
        
        when(facilityRepositoryMock.findById(facilityId)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> {
            equipmentService.addEquipment(facilityId, equipmentMock);
            verify(equipmentRepositoryMock, times(0)).save(equipmentMock);
        });
    }
    
    @Test
    public void testPutEquipment_doesNotOuterJoinWhenThereIsNoExistingEquipment() throws Exception {
        Long facilityId = 1L;
        Facility facility = new Facility();
        Long equipmentId = 1L;
        Equipment equipment = mock(Equipment.class);
        
        when(equipmentRepositoryMock.findById(equipmentId)).thenReturn(Optional.empty());
        when(facilityRepositoryMock.findById(facilityId)).thenReturn(Optional.of(facility));
                
        equipmentService.putEquipment(facilityId, equipmentId, equipment);
       
        verify(equipment, times(0)).outerJoin(Mockito.any(Equipment.class));
        verify(equipment, times(1)).setId(equipmentId);
        verify(equipmentRepositoryMock, times(1)).save(equipment);
    }
    
    @Test
    public void testPutEquipment_doesOuterJoinWhenThereIsAnExistingEquipment() throws Exception {
        Long facilityId = 1L;
        Facility facility = new Facility();
        Long equipmentId = 1L;
        Equipment equipment = mock(Equipment.class);
        Equipment existingEquipment = new Equipment(equipmentId, new Facility(), null, null, null, null, null, null, null);
        
        when(equipmentRepositoryMock.findById(equipmentId)).thenReturn(Optional.of(existingEquipment));
        when(facilityRepositoryMock.findById(facilityId)).thenReturn(Optional.of(facility));
                
        equipmentService.putEquipment(facilityId, equipmentId, equipment);
       
        verify(equipment, times(1)).outerJoin(existingEquipment);
        verify(equipment, times(1)).setId(equipmentId);
        verify(equipmentRepositoryMock, times(1)).save(equipment);
    }
    
    @Test
    public void testPatchEquipment_success() throws Exception {
        Long id = 1L;
        Equipment updatedEquipmentMock = mock(Equipment.class);
        Equipment existingEquipment = new Equipment(id, new Facility(), null, null, null, null, null, null, null);
        
        when(equipmentRepositoryMock.findById(id)).thenReturn(Optional.of(existingEquipment));
 
        equipmentService.patchEquipment(id, updatedEquipmentMock);
       
        verify(updatedEquipmentMock, times(1)).outerJoin(existingEquipment);
        verify(equipmentRepositoryMock, times(1)).save(updatedEquipmentMock);
    }
    
    @Test
    public void testPatchEquipment_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        Equipment equipment = new Equipment();
        
        when(equipmentRepositoryMock.existsById(id)).thenReturn(false);
      
        assertThrows(EntityNotFoundException.class, () -> {
            equipmentService.patchEquipment(id, equipment);
            verify(equipmentRepositoryMock, times(0)).save(Mockito.any(Equipment.class));
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
