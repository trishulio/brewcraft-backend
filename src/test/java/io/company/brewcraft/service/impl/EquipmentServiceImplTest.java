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

import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.repository.EquipmentRepository;
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

        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(equipmentRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedEquipment);

        Page<Equipment> actualEquipment = equipmentService.getAllEquipment(null, null, null, null, 0, 100, new String[]{"id"}, true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(expectedEquipment, actualEquipment);
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
