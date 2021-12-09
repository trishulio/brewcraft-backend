package io.company.brewcraft.service.exception;

import javax.measure.Quantity;
import javax.measure.Unit;

import io.company.brewcraft.model.BaseQuantityUnitAccessor;
import io.company.brewcraft.util.QuantityCalculator;

public class IncompatibleQuantityUnitException extends IllegalArgumentException {
    private static final long serialVersionUID = 5114542775018005333L;

    public IncompatibleQuantityUnitException() {
        super();
    }

    public IncompatibleQuantityUnitException(String message) {
        super(message);
    }

    public static void validate(Quantity<?> quantity, BaseQuantityUnitAccessor unitAccessor) {
        if (!QuantityCalculator.INSTANCE.isCompatibleQtyForUnitAccessor(quantity, unitAccessor)) {
            throw new IncompatibleQuantityUnitException(buildErrorMessage(quantity, unitAccessor));
        }
    }

    private static String buildErrorMessage(Quantity<?> quantity, BaseQuantityUnitAccessor unitAccessor) {
        Unit<?> qtyUnit = null;
        Unit<?> accessorUnit = null;

        if (quantity != null) {
            qtyUnit = quantity.getUnit();
        }

        if(unitAccessor != null) {
            accessorUnit = unitAccessor.getBaseQuantityUnit();
        }

        return String.format("Quantity Unit: %s is not compatible with accessor unit: %s", qtyUnit, accessorUnit);
    }
}
