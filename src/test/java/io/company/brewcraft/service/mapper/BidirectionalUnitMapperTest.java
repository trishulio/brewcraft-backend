package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tec.units.ri.unit.Units;

public class BidirectionalUnitMapperTest {

    private UnitMapper mapper;

    @BeforeEach
    public void init() {
        mapper = new UnitMapperBidirectional();
    }

    @Test
    public void testGetSymbol_ReturnsKg_WhenUnitIsKilogram() {
        assertEquals("kg", mapper.getSymbol(Units.KILOGRAM));
    }

    @Test
    public void testGetUnit_ReturnsKilogram_WhenSymbolIsKg() {
        assertEquals(Units.KILOGRAM, mapper.getUnit("kg"));
    }

    @Test
    public void testGetSymbol_ReturnsLitre_WhenUnitIsLitre() {
        assertEquals("litre", mapper.getSymbol(Units.LITRE));
    }

    @Test
    public void testGetUnit_ReturnsLitre_WhenSymbolIsLitre() {
        assertEquals(Units.LITRE, mapper.getUnit("litre"));
    }
}
