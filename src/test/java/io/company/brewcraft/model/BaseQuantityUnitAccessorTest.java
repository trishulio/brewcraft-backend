package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class BaseQuantityUnitAccessorTest {
    @Test
    public void testValidate_ThrowsException_WhenUnitsAreIncompatible() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(Units.LITRE).when(accessor).getBaseQuantityUnit();

        assertThrows(IncompatibleQuantityUnitException.class, () -> BaseQuantityUnitAccessor.validateUnit(accessor, Quantities.getQuantity("10 kg")));
    }

    @Test
    public void testValidate_DoesNothing_WhenUnitsAreNotIncompatible() {
        BaseQuantityUnitAccessor accessor = mock(BaseQuantityUnitAccessor.class);
        doReturn(Units.GRAM).when(accessor).getBaseQuantityUnit();

        BaseQuantityUnitAccessor.validateUnit(accessor, Quantities.getQuantity("10 kg"));
    }
}
