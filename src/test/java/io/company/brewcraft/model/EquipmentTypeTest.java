package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentTypeTest {
    private EquipmentType equipmentType;

    @BeforeEach
    public void init() {
        equipmentType = new EquipmentType();
    }

    @Test
    public void testIdArgConstructor() {
        equipmentType = new EquipmentType(99L);
        assertEquals(99L, equipmentType.getId());
    }

    @Test
    public void testAllArgConstructor() {
        equipmentType = new EquipmentType(1L, "WHIRLPOOL", LocalDateTime.of(1999, 12, 12, 0, 0), LocalDateTime.of(2000, 12, 12, 0, 0), 1);

        assertEquals(99L, equipmentType.getId());
        assertEquals("FINAL", equipmentType.getName());
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), equipmentType.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), equipmentType.getLastUpdated());
        assertEquals(1, equipmentType.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(equipmentType.getId());
        equipmentType.setId(1L);
        assertEquals(1L, equipmentType.getId());
    }

    @Test
    public void testAccessName() {
        assertNull(equipmentType.getName());
        equipmentType.setName("STATUS_NAME");
        assertEquals("STATUS_NAME", equipmentType.getName());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(equipmentType.getCreatedAt());
        equipmentType.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), equipmentType.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(equipmentType.getLastUpdated());
        equipmentType.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), equipmentType.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(equipmentType.getVersion());
        equipmentType.setVersion(1);
        assertEquals(1, equipmentType.getVersion());
    }
}
