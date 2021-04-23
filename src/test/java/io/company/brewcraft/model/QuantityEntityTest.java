package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuantityEntityTest {

    private QuantityEntity quantity;

    @BeforeEach
    public void init() {
        quantity = new QuantityEntity();
    }

    @Test
    public void testAllArgsConstructor() {
        quantity = new QuantityEntity(new UnitEntity("KG"), new BigDecimal("100"));
        assertEquals(new UnitEntity("KG"), quantity.getUnit());
        assertEquals(new BigDecimal("100"), quantity.getValue());
    }

    @Test
    public void testAccessUnit() {
        assertNull(quantity.getUnit());
        quantity.setUnit(new UnitEntity("L"));
        assertEquals(new UnitEntity("L"), quantity.getUnit());
    }

    @Test
    public void testAccessValue() {
        assertNull(quantity.getValue());
        quantity.setValue(new BigDecimal("99"));
        assertEquals(new BigDecimal("99"), quantity.getValue());
    }
}
