package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.model.ShipmentStatus;

public class ShipmentStatusMapperTest {
    private ShipmentStatusMapper mapper;

    @BeforeEach
    public void init() {
        mapper = ShipmentStatusMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnPojo_WhenIdIsNotNull() {
        ShipmentStatus shipmentStatus = mapper.fromDto(99L);
        ShipmentStatus expected = new ShipmentStatus(99L);

        assertEquals(expected, shipmentStatus);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(mapper.fromDto((Long) null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        ShipmentStatus shipmentStatus = mapper.fromDto(new ShipmentStatusDto(99L));
        ShipmentStatus expected = new ShipmentStatus(99L);

        assertEquals(expected, shipmentStatus);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto((ShipmentStatusDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        ShipmentStatusDto shipmentStatus = mapper.toDto(new ShipmentStatus(99L));

        ShipmentStatusDto expected = new ShipmentStatusDto(99L);
        assertEquals(expected, shipmentStatus);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
