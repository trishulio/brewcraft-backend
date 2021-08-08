package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.ProductMeasureValue;
import io.company.brewcraft.repository.ProductMeasureValueRepository;
import io.company.brewcraft.service.ProductMeasureValueService;

public class ProductMeasureValueServiceImplTest {
    
    private ProductMeasureValueService productMeasureValueService;
    
    private ProductMeasureValueRepository productMeasureValueRepositoryMock;
    
    @BeforeEach
    public void init() {
        productMeasureValueRepositoryMock = Mockito.mock(ProductMeasureValueRepository.class);
        productMeasureValueService = new ProductMeasureValueServiceImpl(productMeasureValueRepositoryMock);
    }

    @Test
    public void testMerge_returnsUpdatedValues() throws Exception {  
        List<ProductMeasureValue> existingValues = new ArrayList<>();
        existingValues.add(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));
        
        List<ProductMeasureValue> updatedValues = new ArrayList<>();
        updatedValues.add(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("150"), new Product()));
        updatedValues.add(new ProductMeasureValue(2L,new Measure(2L, "ibu", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("200"), new Product()));
      
        List<ProductMeasureValue> returnedValues = productMeasureValueService.merge(existingValues, updatedValues);
        
        assertEquals(2, returnedValues.size());
        assertEquals(1L, returnedValues.get(0).getId());
        assertEquals(new Product(), returnedValues.get(0).getProduct());
        assertEquals(new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), returnedValues.get(0).getMeasure());
        assertEquals(new BigDecimal("150"), returnedValues.get(0).getValue());
        assertEquals(2L, returnedValues.get(1).getId());
        assertEquals(new Product(), returnedValues.get(1).getProduct());
        assertEquals(new Measure(2L, "ibu", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), returnedValues.get(1).getMeasure());
        assertEquals(new BigDecimal("200"), returnedValues.get(1).getValue());
    }
    
    @Test
    public void testMerge_successWhenExistingValuesIsNull() throws Exception {  
        List<ProductMeasureValue> updatedValues = new ArrayList<>();
        updatedValues.add(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));
        updatedValues.add(new ProductMeasureValue(2L,new Measure(2L, "ibu", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("200"), new Product()));
    
        List<ProductMeasureValue> returnedValues = productMeasureValueService.merge(null, updatedValues);
        
        assertEquals(2, returnedValues.size());
        assertEquals(1L, returnedValues.get(0).getId());
        assertEquals(new Product(), returnedValues.get(0).getProduct());
        assertEquals(new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), returnedValues.get(0).getMeasure());
        assertEquals(new BigDecimal("100"), returnedValues.get(0).getValue());
        assertEquals(2L, returnedValues.get(1).getId());
        assertEquals(new Product(), returnedValues.get(1).getProduct());
        assertEquals(new Measure(2L, "ibu", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), returnedValues.get(1).getMeasure());
        assertEquals(new BigDecimal("200"), returnedValues.get(1).getValue());
    }
    
    @Test
    public void testMerge_successWhenUpdatedValuesIsNull() throws Exception {  
        List<ProductMeasureValue> existingValues = new ArrayList<>();
        existingValues.add(new ProductMeasureValue(1L,new Measure(1L, "abv", LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1), new BigDecimal("100"), new Product()));
   
        List<ProductMeasureValue> returnedValues = productMeasureValueService.merge(existingValues, null);
        
        assertEquals(0, returnedValues.size());
    }
    
    @Test
    public void testProductMeasureValueService_classIsTransactional() throws Exception {
        Transactional transactional = productMeasureValueService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testProductMeasureValueService_methodsAreNotTransactional() throws Exception {
        Method[] methods = productMeasureValueService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
