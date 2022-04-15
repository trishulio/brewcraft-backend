package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentTypeDtoTest {
    private EquipmentTypeDto equipmentTypeDto;

    @BeforeEach
    public void init() {
        equipmentTypeDto = new EquipmentTypeDto();
    }

    @Test
    public void testIdArgConstructor() {
        equipmentTypeDto = new EquipmentTypeDto(1L);
        assertEquals(99L, equipmentTypeDto.getId());
    }

    @Test
    public void testAllArgConstructor() {
        equipmentTypeDto = new EquipmentTypeDto(1L, "BRITETANK", 1);

        assertEquals(1L, equipmentTypeDto.getId());
        assertEquals("FINAL", equipmentTypeDto.getName());
        assertEquals(1, equipmentTypeDto.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(equipmentTypeDto.getId());
        equipmentTypeDto.setId(1L);
        assertEquals(1L, equipmentTypeDto.getId());
    }

    @Test
    public void testAccessName() {
        assertNull(equipmentTypeDto.getName());
        equipmentTypeDto.setName("STATUS_NAME");
        assertEquals("STATUS_NAME", equipmentTypeDto.getName());
    }

    @Test
    public void testAccessVersion() {
        assertNull(equipmentTypeDto.getVersion());
        equipmentTypeDto.setVersion(1);
        assertEquals(1, equipmentTypeDto.getVersion());
    }
}
