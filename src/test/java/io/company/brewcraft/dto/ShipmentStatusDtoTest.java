package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipmentStatusDtoTest {
    private ShipmentStatusDto dto;

    @BeforeEach
    public void init() {
        dto = new ShipmentStatusDto();
    }

    @Test
    public void testAllArgConstructor_SetsValuesToAllFields() {
        dto = new ShipmentStatusDto("RECEIVED");

        assertEquals("RECEIVED", dto.getName());
    }

    @Test
    public void testAccessName() {
        assertNull(dto.getName());
        dto.setName("FINAL");
        assertEquals("FINAL", dto.getName());
    }
}
