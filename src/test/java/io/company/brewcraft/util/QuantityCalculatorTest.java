package io.company.brewcraft.util;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseQuantityUnitAccessor;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class QuantityCalculatorTest {

    private QuantityCalculator calc;

    @BeforeEach
    public void init() {
        calc = QuantityCalculator.INSTANCE;
    }

    @Test
    public void subtractMass_Success() {
        Quantity<Mass> q1 = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM);
        Quantity<Mass> q2 = Quantities.getQuantity(new BigDecimal("25"), SupportedUnits.KILOGRAM);
        Quantity<Mass> result = (Quantity<Mass>) calc.subtract(q1, q2);

        assertEquals(Quantities.getQuantity(new BigDecimal("75"), SupportedUnits.KILOGRAM), result);
    }

    @Test
    public void subtractVolume_Success() {
        Quantity<Volume> q1 = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE);
        Quantity<Volume> q2 = Quantities.getQuantity(new BigDecimal("25"), SupportedUnits.LITRE);
        Quantity<Volume> result = (Quantity<Volume>) calc.subtract(q1, q2);

        assertEquals(Quantities.getQuantity(new BigDecimal("75"), SupportedUnits.LITRE), result);
    }

    @Test
    public void subtractAmmount_Succes() {
        Quantity<AmountOfSubstance> q1 = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH);
        Quantity<AmountOfSubstance> q2 = Quantities.getQuantity(new BigDecimal("25"), SupportedUnits.EACH);
        Quantity<AmountOfSubstance> result = (Quantity<AmountOfSubstance>) calc.subtract(q1, q2);

        assertEquals(Quantities.getQuantity(new BigDecimal("75"), SupportedUnits.EACH), result);
    }

    @Test
    public void subtractUnsupportedType_Throws() {
        Quantity<Length> q1 = Quantities.getQuantity(new BigDecimal("100"), Units.METRE);
        Quantity<Length> q2 = Quantities.getQuantity(new BigDecimal("25"), Units.METRE);

        assertThrows(RuntimeException.class, () -> calc.subtract(q1, q2));
    }

    @Test
    public void testToSystemQuantityValueWithDisplayUnit_ReturnsNull_WhenArgIsNull() {
        assertNull(calc.toSystemQuantityValueWithDisplayUnit(null));
    }

    @Test
    public void testToSystemQuantityValueWithDisplayUnit_ReturnsSystemValueWithDisplayUnit_WhenArgIsNotNull() {
        Quantity<?> input = Quantities.getQuantity(new BigDecimal("100000"), Units.GRAM);

        Quantity<?> output = calc.toSystemQuantityValueWithDisplayUnit(input);

        assertEquals(Quantities.getQuantity(new BigDecimal("100"), Units.GRAM), output);
    }

    @Test
    public void testFromSystemQuantityValueWithDisplayUnit_ReturnsNull_WhenArgIsNull() {
        assertNull(calc.fromSystemQuantityValueWithDisplayUnit(null));
    }

    @Test
    public void testFromSystemQuantityValueWithDisplayUnit_ReturnsQuantityInDisplayUnit_WhenArgIsNotNull() {
        Quantity<?> input = Quantities.getQuantity(new BigDecimal("100"), Units.GRAM);

        Quantity<?> output = calc.fromSystemQuantityValueWithDisplayUnit(input);

        assertEquals(Quantities.getQuantity(new BigDecimal("100000"), Units.GRAM), output);
    }

    @Test
    public void testIsCompatibleQtyForUnitAccessor_ReturnsTrue_WhenQuantityIsNull() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);

        assertTrue(calc.isCompatibleQtyForUnitAccessor(null, accessor));
    }

    @Test
    public void testIsCompatibleQtyForUnitAccessor_ReturnsTrue_WhenUnitAccessorIsNull() {
        assertTrue(calc.isCompatibleQtyForUnitAccessor(Quantities.getQuantity("10 kg"), null));
    }

    @Test
    public void testIsCompatibleQtyForUnitAccessor_ReturnsTrue_WhenAccessorQuantityIsNull() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(null).when(accessor).getBaseQuantityUnit();

        assertTrue(calc.isCompatibleQtyForUnitAccessor(Quantities.getQuantity("10 kg"), accessor));
    }

    @Test
    public void testIsCompatibleQtyForUnitAccessor_ReturnsTrue_WhenAccessorQuantityIsCompatibleWithUnit() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(Units.GRAM).when(accessor).getBaseQuantityUnit();

        assertTrue(calc.isCompatibleQtyForUnitAccessor(Quantities.getQuantity("10 kg"), accessor));
    }

    @Test
    public void testIsCompatibleQtyForUnitAccessor_ReturnsFalse_WhenAccessorQuantityIsNotCompatibleWithUnit() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(Units.LITRE).when(accessor).getBaseQuantityUnit();

        assertFalse(calc.isCompatibleQtyForUnitAccessor(Quantities.getQuantity("10 kg"), accessor));
    }
}
