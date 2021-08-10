package io.company.brewcraft.util;

import static org.junit.Assert.*;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Volume;

import org.junit.jupiter.api.Test;

import tec.uom.se.quantity.Quantities;

public class SupportedUnitsTest {

    @Test
    public void validateSupportedUnitSymbols() {
        assertEquals("each", SupportedUnits.EACH.getSymbol());
        assertEquals("mg", SupportedUnits.MILLIGRAM.getSymbol());
        assertEquals("g", SupportedUnits.GRAM.getSymbol());
        assertEquals("kg", SupportedUnits.KILOGRAM.getSymbol());
        assertEquals("ml", SupportedUnits.MILLILITRE.getSymbol());
        assertEquals("l", SupportedUnits.LITRE.getSymbol());
        assertEquals("hl", SupportedUnits.HECTOLITRE.getSymbol());
    }

    @Test
    public void validateVolumeUnitConversions() {
        Quantity<Volume> quantity = Quantities.getQuantity(1, SupportedUnits.LITRE);
        assertEquals(1000.0, quantity.to(SupportedUnits.MILLILITRE).getValue());
        assertEquals(0.01, quantity.to(SupportedUnits.HECTOLITRE).getValue());
    }

    @Test
    public void validateMassUnitConversions() {
        Quantity<Mass> quantity = Quantities.getQuantity(1, SupportedUnits.KILOGRAM);
        assertEquals(1000000.0, quantity.to(SupportedUnits.MILLIGRAM).getValue());
        assertEquals(1000.0, quantity.to(SupportedUnits.GRAM).getValue());
    }
}
