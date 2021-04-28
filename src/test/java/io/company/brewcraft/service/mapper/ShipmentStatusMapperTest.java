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
        ShipmentStatus status = mapper.fromDto("FINAL");
        ShipmentStatus expected = new ShipmentStatus("FINAL");

        assertEquals(expected, status);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(mapper.fromDto((String) null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        ShipmentStatus status = mapper.fromDto(new ShipmentStatusDto("FINAL"));
        ShipmentStatus expected = new ShipmentStatus("FINAL");

        assertEquals(expected, status);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(mapper.fromDto((ShipmentStatusDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        ShipmentStatusDto status = mapper.toDto(new ShipmentStatus("FINAL"));

        ShipmentStatusDto expected = new ShipmentStatusDto("FINAL");
        assertEquals(expected, status);
    }
    
    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
