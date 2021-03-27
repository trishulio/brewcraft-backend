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

import io.company.brewcraft.model.ProductMeasure;
import io.company.brewcraft.repository.ProductMeasureRepository;
import io.company.brewcraft.service.ProductMeasureService;

public class ProductMeasureServiceImplTest {
    
    private ProductMeasureService productMeasureService;
    
    private ProductMeasureRepository productMeasureRepositoryMock;
    
    @BeforeEach
    public void init() {
        productMeasureRepositoryMock = Mockito.mock(ProductMeasureRepository.class);
        productMeasureService = new ProductMeasureServiceImpl(productMeasureRepositoryMock);
    }

    @Test
    public void testGetAllProductMeasures_returnsProductsMeasures() throws Exception {                
        List<ProductMeasure> productMeasures = List.of(new ProductMeasure("abv"));
        
        when(productMeasureRepositoryMock.findAll()).thenReturn(productMeasures);

        List<ProductMeasure> actualProductsMeasures = productMeasureService.getAllProductMeasures();

        assertEquals(List.of(new ProductMeasure("abv")), actualProductsMeasures);
    }
    
    @Test
    public void testProductService_classIsTransactional() throws Exception {
        Transactional transactional = productMeasureService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testProductService_methodsAreNotTransactional() throws Exception {
        Method[] methods = productMeasureService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
