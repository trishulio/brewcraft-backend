package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.repository.MeasureRepository;
import io.company.brewcraft.service.MeasureService;

public class MeasureServiceImplTest {
    
    private MeasureService measureService;
    
    private MeasureRepository measureRepositoryMock;
    
    @BeforeEach
    public void init() {
        measureRepositoryMock = Mockito.mock(MeasureRepository.class);
        measureService = new MeasureServiceImpl(measureRepositoryMock);
    }

    @Test
    public void testGetAllMeasures_returnsProductsMeasures() throws Exception {                
        List<Measure> measures = List.of(new Measure(1L, "abv"));
        
        when(measureRepositoryMock.findAll()).thenReturn(measures);

        List<Measure> actualMeasures = measureService.getAllMeasures();

        assertEquals(List.of(new Measure(1L, "abv")), actualMeasures);
    }
    
    @Test
    public void testProductService_classIsTransactional() throws Exception {
        Transactional transactional = measureService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testProductService_methodsAreNotTransactional() throws Exception {
        Method[] methods = measureService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
