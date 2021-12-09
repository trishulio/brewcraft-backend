package io.company.brewcraft.model;

import javax.measure.Unit;

public interface BaseQuantityUnitAccessor {
    Unit<?> getBaseQuantityUnit();

    void setBaseQuantityUnit(Unit<?> baseQuantityUnit);
}
