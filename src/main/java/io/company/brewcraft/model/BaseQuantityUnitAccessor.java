package io.company.brewcraft.model;

import javax.measure.Quantity;
import javax.measure.Unit;

import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.util.QuantityCalculator;

public interface BaseQuantityUnitAccessor {
    static void validateUnit(BaseQuantityUnitAccessor unitAccessor, Quantity<?> quantity) {
        if (!QuantityCalculator.INSTANCE.isCompatibleQtyForUnitAccessor(quantity, unitAccessor)) {
            Unit<?> qtyUnit = null;
            Unit<?> accessorUnit = null;

            if (quantity != null) {
                qtyUnit = quantity.getUnit();
            }

            if(unitAccessor != null) {
                accessorUnit = unitAccessor.getBaseQuantityUnit();
            }

            String error = String.format("Quantity Unit: %s is not compatible with accessor unit: %s", qtyUnit, accessorUnit);
            throw new IncompatibleQuantityUnitException(error);
        }
    }

    Unit<?> getBaseQuantityUnit();

    void setBaseQuantityUnit(Unit<?> baseQuantityUnit);
}
