package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import javax.measure.Quantity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.QuantityDto;
import tec.units.ri.quantity.Quantities;
import tec.units.ri.unit.Units;

public class QuantityMapperTest {

    QuantityMapper mapper;

    @BeforeEach
    public void init() {
        mapper = QuantityMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsDto_WhenQuantityIsNotNull() {
        QuantityDto dto = mapper.toDto(Quantities.getQuantity(100, Units.KILOGRAM));

        assertEquals("kg", dto.getSymbol());
        assertEquals(100, dto.getValue());
    }

    @Test
    public void testFromDto_ReturnsQuantity_WhenDtoIsNotNull() {
        Quantity<?> quantity = mapper.fromDto(new QuantityDto("kg", 100));
        assertEquals(Units.KILOGRAM, quantity.getUnit());
        assertEquals(100, quantity.getValue());
    }
}
