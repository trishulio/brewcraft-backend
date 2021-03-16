package io.company.brewcraft.service.mapper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import javax.measure.Unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.UnitDto;
import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.utils.SupportedUnits;

public class QuantityUnitMapperTest {

    private QuantityUnitMapper mapper;

    @BeforeEach
    public void init() {
        mapper = QuantityUnitMapper.INSTANCE;
    }

    @Test
    public void testFromSymbol_ReturnsNull_WhenSymbolIsNull() {
        assertNull(mapper.fromSymbol(null));
    }

    @Test
    public void testFromSymbol_ReturnsPojoMatchingSymbol_WhenSymbolIsNotNull() {
        Map<String, Unit<?>> symbolsMap = new HashMap<>();
        symbolsMap.put(SupportedUnits.EACH.toString(), SupportedUnits.EACH);
        symbolsMap.put(SupportedUnits.MILLIGRAM.toString(), SupportedUnits.MILLIGRAM);
        symbolsMap.put(SupportedUnits.GRAM.toString(), SupportedUnits.GRAM);
        symbolsMap.put(SupportedUnits.KILOGRAM.toString(), SupportedUnits.KILOGRAM);
        symbolsMap.put(SupportedUnits.MILLILITRE.toString(), SupportedUnits.MILLILITRE);
        symbolsMap.put(SupportedUnits.LITRE.toString(), SupportedUnits.LITRE);
        symbolsMap.put(SupportedUnits.HECTOLITRE.toString(), SupportedUnits.HECTOLITRE);

        symbolsMap.forEach((symbol, unit) -> assertSame(unit, mapper.fromSymbol(symbol)));
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        mapper = spy(mapper);
        doReturn(SupportedUnits.KILOGRAM).when(mapper).fromSymbol("TEST_SYMBOL");

        Unit<?> unit = mapper.fromEntity(new UnitEntity("TEST_SYMBOL"));
        assertSame(SupportedUnits.KILOGRAM, unit);
    }

    @Test
    public void testFromEntity_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.fromEntity(null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        mapper = spy(mapper);
        doReturn(SupportedUnits.KILOGRAM).when(mapper).fromSymbol("TEST_SYMBOL");

        Unit<?> unit = mapper.fromDto(new UnitDto("TEST_SYMBOL"));
        assertSame(SupportedUnits.KILOGRAM, unit);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }
}
