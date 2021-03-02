package io.company.brewcraft.service.mapper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import javax.measure.Unit;
import javax.measure.quantity.AmountOfSubstance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.UnitDto;
import io.company.brewcraft.model.UnitEntity;
import tec.units.ri.quantity.QuantityDimension;
import tec.units.ri.unit.BaseUnit;
import tec.units.ri.unit.MetricPrefix;
import tec.units.ri.unit.Units;

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
        symbolsMap.put(Units.AMPERE.toString(), Units.AMPERE);
        symbolsMap.put(Units.BECQUEREL.toString(), Units.BECQUEREL);
        symbolsMap.put(Units.CANDELA.toString(), Units.CANDELA);
        symbolsMap.put(Units.CELSIUS.toString(), Units.CELSIUS);
        symbolsMap.put(Units.COULOMB.toString(), Units.COULOMB);
        symbolsMap.put(Units.CUBIC_METRE.toString(), Units.CUBIC_METRE);
        symbolsMap.put(Units.DAY.toString(), Units.DAY);
        symbolsMap.put(Units.FARAD.toString(), Units.FARAD);
        symbolsMap.put(Units.GRAM.toString(), Units.GRAM);
        symbolsMap.put(Units.GRAY.toString(), Units.GRAY);
        symbolsMap.put(Units.HENRY.toString(), Units.HENRY);
        symbolsMap.put(Units.HERTZ.toString(), Units.HERTZ);
        symbolsMap.put(Units.HOUR.toString(), Units.HOUR);
        symbolsMap.put(Units.JOULE.toString(), Units.JOULE);
        symbolsMap.put(Units.KATAL.toString(), Units.KATAL);
        symbolsMap.put(Units.KELVIN.toString(), Units.KELVIN);
        symbolsMap.put(Units.KILOGRAM.toString(), Units.KILOGRAM);
        symbolsMap.put(Units.KILOMETRE_PER_HOUR.toString(), Units.KILOMETRE_PER_HOUR);
        symbolsMap.put(Units.LITRE.toString(), Units.LITRE);
        symbolsMap.put(Units.LUMEN.toString(), Units.LUMEN);
        symbolsMap.put(Units.LUX.toString(), Units.LUX);
        symbolsMap.put(Units.METRE.toString(), Units.METRE);
        symbolsMap.put(Units.METRE_PER_SECOND.toString(), Units.METRE_PER_SECOND);
        symbolsMap.put(Units.MINUTE.toString(), Units.MINUTE);
        symbolsMap.put(Units.MOLE.toString(), Units.MOLE);
        symbolsMap.put(Units.NEWTON.toString(), Units.NEWTON);
        symbolsMap.put(Units.OHM.toString(), Units.OHM);
        symbolsMap.put(Units.PERCENT.toString(), Units.PERCENT);
        symbolsMap.put(Units.RADIAN.toString(), Units.RADIAN);
        symbolsMap.put(Units.SECOND.toString(), Units.SECOND);
        symbolsMap.put(Units.SIEMENS.toString(), Units.SIEMENS);
        symbolsMap.put(Units.SIEVERT.toString(), Units.SIEVERT);
        symbolsMap.put(Units.SQUARE_METRE.toString(), Units.SQUARE_METRE);
        symbolsMap.put(Units.STERADIAN.toString(), Units.STERADIAN);
        symbolsMap.put(Units.TESLA.toString(), Units.TESLA);
        symbolsMap.put(Units.VOLT.toString(), Units.VOLT);
        symbolsMap.put(Units.WATT.toString(), Units.WATT);
        symbolsMap.put(Units.WEBER.toString(), Units.WEBER);
        symbolsMap.put(Units.WEEK.toString(), Units.WEEK);
        symbolsMap.put(Units.YEAR.toString(), Units.YEAR);

        symbolsMap.forEach((symbol, unit) -> assertSame(unit, mapper.fromSymbol(symbol)));

        // Custom values tests
        assertEquals(new BaseUnit<AmountOfSubstance>("each", QuantityDimension.AMOUNT_OF_SUBSTANCE), mapper.fromSymbol("each"));
        assertEquals(MetricPrefix.HECTO(Units.LITRE), mapper.fromSymbol("hl"));
    }

    @Test
    public void testFromEntity_ReturnsPojo_WhenEntityIsNotNull() {
        mapper = spy(mapper);
        doReturn(Units.KILOGRAM).when(mapper).fromSymbol("TEST_SYMBOL");

        Unit<?> unit = mapper.fromEntity(new UnitEntity("TEST_SYMBOL"));
        assertSame(Units.KILOGRAM, unit);
    }

    @Test
    public void testFromEntity_ReturnsNull_WhenEntityIsNull() {
        assertNull(mapper.fromEntity(null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        mapper = spy(mapper);
        doReturn(Units.KILOGRAM).when(mapper).fromSymbol("TEST_SYMBOL");

        Unit<?> unit = mapper.fromDto(new UnitDto("TEST_SYMBOL"));
        assertSame(Units.KILOGRAM, unit);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }
}
