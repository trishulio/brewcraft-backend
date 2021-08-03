package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
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
    public void testGetAllMeasures_returnsMeasures() throws Exception {                        
        Page<Measure> expectedMeasureEntities = new PageImpl<>(List.of(new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)));
        
        ArgumentCaptor<Pageable> pageableArgument = ArgumentCaptor.forClass(Pageable.class);
        
        when(measureRepositoryMock.findAll(ArgumentMatchers.<Specification<Measure>>any(), pageableArgument.capture())).thenReturn(expectedMeasureEntities);

        Page<Measure> measuresPage = measureService.getMeasures(null, 0, 100, new TreeSet<>(List.of("id")), true);

        assertEquals(List.of(new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), measuresPage.getContent());
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
