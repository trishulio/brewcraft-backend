package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import javax.measure.Quantity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class QuantityMapperTest {

    QuantityMapper mapper;

    @BeforeEach
    public void init() {
        mapper = QuantityMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsDto_WhenQuantityIsNotNull() {
        QuantityDto dto = mapper.toDto(Quantities.getQuantity(new BigDecimal(100), SupportedUnits.KILOGRAM));

        assertEquals("kg", dto.getSymbol());
        assertEquals(new BigDecimal(100), dto.getValue());
    }

    @Test
    public void testToDto_ReturnsNull_WhenQuantityIsNull() {
        assertNull(mapper.toDto(null));
    }

    @Test
    public void testToEntity_ReturnsNull_WhenQuantityIsNull() {
        assertNull(mapper.toEntity(null));
    }

    @Test
    public void testFromDto_ReturnsQuantity_WhenDtoIsNotNull() {
        Quantity<?> quantity = mapper.fromDto(new QuantityDto("kg", new BigDecimal(100)));
        assertEquals(SupportedUnits.KILOGRAM, quantity.getUnit());
        assertEquals(new BigDecimal(100), quantity.getValue());
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto(null));
    }

    @Test
    public void testFromEntity_ReturnsNull_WhenEntityIsNotNull() {
        assertNull(mapper.fromEntity(null));
    }
}
