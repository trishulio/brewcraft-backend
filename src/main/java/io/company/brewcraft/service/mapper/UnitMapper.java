package io.company.brewcraft.service.mapper;

import javax.measure.Unit;

public interface UnitMapper {
    UnitMapper INSTANCE = new UnitMapperBidirectional();
    
    String getSymbol(Unit<?> unit);
    
    Unit<?> getUnit(String symbol);
}
