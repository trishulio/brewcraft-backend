package io.company.brewcraft.service.exception;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseQuantityUnitAccessor;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class IncompatibleQuantityUnitExceptionTest {

    private Exception ex;

    @BeforeEach
    public void init() {
        ex = new IncompatibleQuantityUnitException();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    public void testConstructor_String_SetsMessage() {
        ex = new IncompatibleQuantityUnitException("Message");

        assertEquals("Message", ex.getMessage());
    }
    
    @Test
    public void testValidateUnit_ThrowsException_WhenUnitsAreIncompatible() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(Units.LITRE).when(accessor).getBaseQuantityUnit();

        assertThrows(IncompatibleQuantityUnitException.class, () -> IncompatibleQuantityUnitException.validateUnit(accessor, Quantities.getQuantity("10 kg")));
    }

    @Test
    public void testValidateUnit_DoesNothing_WhenUnitsAreNotIncompatible() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(Units.GRAM).when(accessor).getBaseQuantityUnit();

        IncompatibleQuantityUnitException.validateUnit(accessor, Quantities.getQuantity("10 kg"));
    }
    
    @Test
    public void testValidateCompatibleQuantities_ThrowsException_WhenUnitsAreIncompatible() {
        assertThrows(IncompatibleQuantityUnitException.class, () -> IncompatibleQuantityUnitException.validateCompatibleQuantities(Quantities.getQuantity("10 l"), Quantities.getQuantity("10 kg")));
    }

    @Test
    public void testValidateCompatibleQuantities_DoesNothing_WhenUnitsAreNotIncompatible() {
        IncompatibleQuantityUnitException.validateCompatibleQuantities(Quantities.getQuantity("10 g"), Quantities.getQuantity("10 kg"));
    }
}
