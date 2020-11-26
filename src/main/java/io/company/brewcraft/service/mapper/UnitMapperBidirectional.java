package io.company.brewcraft.service.mapper;

import javax.measure.Unit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import tec.units.ri.unit.Units;

public class UnitMapperBidirectional implements UnitMapper {
    private static final Logger logger = LoggerFactory.getLogger(UnitMapperBidirectional.class);

    private BiMap<Unit<?>, String> map;

    public UnitMapperBidirectional() {
        this(HashBiMap.create());
    }

    protected UnitMapperBidirectional(BiMap<Unit<?>, String> map) {
        this.map = map;
        this.map.put(Units.KILOGRAM, "kg");
        this.map.put(Units.LITRE, "litre");
    }

    @Override
    public String getSymbol(Unit<?> unit) {
        String symbol = this.map.get(unit);
        assertNotNull(symbol, unit.getName());
        return symbol;
    }

    @Override
    public Unit<?> getUnit(String symbol) {
        Unit<?> unit = this.map.inverse().get(symbol.toLowerCase());
        assertNotNull(unit, symbol);
        return unit;
    }

    private void assertNotNull(Object o, String key) {
        if (o == null) {
            String msg = String.format("Received null value for key: '%s'. Either no mapping exist or symbol is invalid", key);
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }
}
