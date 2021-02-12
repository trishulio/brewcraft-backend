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
import io.company.brewcraft.model.FacilityAddress;
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
        FacilityEntity facility1 = new FacilityEntity();
        FacilityEntity facility2 = new FacilityEntity();
                
        List<FacilityEntity> facilities = Arrays.asList(facility1, facility2);
        
        Page<FacilityEntity> expectedFacilities = new PageImpl<>(facilities);
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);

        when(facilityRepositoryMock.findAll(pageableArgument.capture())).thenReturn(expectedFacilities);

        Page<FacilityEntity> actualFacilities = facilityService.getAllFacilities(0, 100, new HashSet<>(Arrays.asList("id")), true);

        assertEquals(0, pageableArgument.getValue().getPageNumber());
        assertEquals(100, pageableArgument.getValue().getPageSize());
        assertEquals(true, pageableArgument.getValue().getSort().get().findFirst().get().isAscending());
        assertEquals("id", pageableArgument.getValue().getSort().get().findFirst().get().getProperty());
        assertEquals(expectedFacilities, actualFacilities);
    }
    
    @Test
    public void testGetFacility_returnsFacility() throws Exception {
        Long id = 1L;
        Optional<FacilityEntity> expectedFacility = Optional.ofNullable(new FacilityEntity());

        when(facilityRepositoryMock.findById(id)).thenReturn(expectedFacility);

        FacilityEntity actualFacility = facilityService.getFacility(id);

        assertSame(expectedFacility.get(), actualFacility);
    }

    @Test
    public void testAddFacility_SavesFacility() throws Exception {
        FacilityEntity Facility = new FacilityEntity();
        
        facilityService.addFacility(Facility);
        
        verify(facilityRepositoryMock, times(1)).save(Facility);
    }
    
    @Test
    public void testPutFacility_DoesNotOuterJoinWhenThereIsNoExistingFacility() throws Exception {
        Long id = 1L;
        FacilityEntity facility = mock(FacilityEntity.class);
        
        when(facilityRepositoryMock.findById(id)).thenReturn(Optional.empty());
                
        facilityService.putFacility(id, facility);
       
        verify(facility, times(0)).outerJoin(Mockito.any(FacilityEntity.class));
        verify(facility, times(1)).setId(id);
        verify(facilityRepositoryMock, times(1)).save(facility);
    }
    
    @Test
    public void testPutFacility_DoesOuterJoinWhenThereIsAnExistingFacility() throws Exception {
        Long id = 1L;
        FacilityEntity facility = mock(FacilityEntity.class);
        FacilityEntity existingFacility = new FacilityEntity(id, "Facility 1", new FacilityAddress(), null, null, Arrays.asList(), Arrays.asList(), null, null, null);
        
        when(facilityRepositoryMock.findById(id)).thenReturn(Optional.of(existingFacility));
                
        facilityService.putFacility(id, facility);
       
        verify(facility, times(1)).outerJoin(existingFacility);
        verify(facility, times(1)).setId(id);
        verify(facilityRepositoryMock, times(1)).save(facility);
    }
    
    @Test
    public void testPatchFacility_success() throws Exception {
        Long id = 1L;
        FacilityEntity updatedFacilityMock = mock(FacilityEntity.class);
        FacilityEntity existingFacility = new FacilityEntity(id, "Facility 1", new FacilityAddress(),  null, null, Arrays.asList(), Arrays.asList(), null, null, null);
        
        when(facilityRepositoryMock.findById(id)).thenReturn(Optional.of(existingFacility));
 
        facilityService.patchFacility(id, updatedFacilityMock);
       
        verify(updatedFacilityMock, times(1)).outerJoin(existingFacility);
        verify(facilityRepositoryMock, times(1)).save(updatedFacilityMock);
    }
    
    @Test
    public void testPatchFacility_throwsEntityNotFoundException() throws Exception {
        Long id = 1L;
        FacilityEntity facility = new FacilityEntity();
        
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
