package io.company.brewcraft.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import javax.measure.Quantity;
import javax.measure.quantity.AmountOfSubstance;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import org.junit.jupiter.api.Test;

import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class QuantityCalculatorTest {
    
    @Test
    public void subtractMass_Success() {
        Quantity<Mass> q1 = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM);
        Quantity<Mass> q2 = Quantities.getQuantity(new BigDecimal("25"), SupportedUnits.KILOGRAM);
        Quantity<Mass> result = (Quantity<Mass>) QuantityCalculator.subtract(q1, q2);
        
        assertEquals(Quantities.getQuantity(new BigDecimal("75"), SupportedUnits.KILOGRAM), result);
    }
    
    @Test
    public void subtractVolume_Success() {
        Quantity<Volume> q1 = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE);
        Quantity<Volume> q2 = Quantities.getQuantity(new BigDecimal("25"), SupportedUnits.LITRE);
        Quantity<Volume> result = (Quantity<Volume>) QuantityCalculator.subtract(q1, q2);
        
        assertEquals(Quantities.getQuantity(new BigDecimal("75"), SupportedUnits.LITRE), result);
    }
    
    @Test
    public void subtractAmmount_Succes() {
        Quantity<AmountOfSubstance> q1 = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH);
        Quantity<AmountOfSubstance> q2 = Quantities.getQuantity(new BigDecimal("25"), SupportedUnits.EACH);
        Quantity<AmountOfSubstance> result = (Quantity<AmountOfSubstance>) QuantityCalculator.subtract(q1, q2);
        
        assertEquals(Quantities.getQuantity(new BigDecimal("75"), SupportedUnits.EACH), result);
    }
    
    @Test
    public void subtractUnsupportedType_Throws() {
        Quantity<Length> q1 = Quantities.getQuantity(new BigDecimal("100"), Units.METRE);
        Quantity<Length> q2 = Quantities.getQuantity(new BigDecimal("25"), Units.METRE);
        
        assertThrows(RuntimeException.class, () -> QuantityCalculator.subtract(q1, q2));
    }

}
