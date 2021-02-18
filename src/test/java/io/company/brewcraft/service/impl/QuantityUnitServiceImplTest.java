package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Optional;

import javax.measure.Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.repository.QuantityUnitRepository;
import io.company.brewcraft.service.QuantityUnitService;
import tec.units.ri.unit.Units;

public class QuantityUnitServiceImplTest {

    private QuantityUnitService quantityUnitService;

    private QuantityUnitRepository quantityUnitRepositoryMock;
    
    @BeforeEach
    public void init() {
        quantityUnitRepositoryMock = mock(QuantityUnitRepository.class);
        
        quantityUnitService = new  QuantityUnitServiceImpl(quantityUnitRepositoryMock);
    }

    @Test
    public void testGetQuantityUnit_returnsUnit() throws Exception {
        String symbol = "g";
        UnitEntity unitEntity = new UnitEntity("g");
        Optional<UnitEntity> expectedUnitEntity = Optional.ofNullable(unitEntity);

        when(quantityUnitRepositoryMock.findBySymbol(symbol)).thenReturn(expectedUnitEntity);

        Unit<?> returnedUnit = quantityUnitService.get(symbol);

        assertEquals(Units.GRAM, returnedUnit);
    }
    
    @Test
    public void testQuantityUnitService_classIsTransactional() throws Exception {
        Transactional transactional = quantityUnitService.getClass().getAnnotation(Transactional.class);
        
        assertNotNull(transactional);
        assertEquals(transactional.isolation(), Isolation.DEFAULT);
        assertEquals(transactional.propagation(), Propagation.REQUIRED);
    }
    
    @Test
    public void testQuantityUnitService_methodsAreNotTransactional() throws Exception {
        Method[] methods = quantityUnitService.getClass().getMethods();  
        for(Method method : methods) {
            assertFalse(method.isAnnotationPresent(Transactional.class));
        }
    }
}
