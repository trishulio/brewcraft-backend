package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentStatus;

public class UpdateEquipmentDtoTest {
    private UpdateEquipmentDto updateEquipmentDto;

    @BeforeEach
    public void init() {
        updateEquipmentDto = new UpdateEquipmentDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "equipment1";
        Long typeId = 2L;
        Long facilityId = 3L;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        int version = 1;

        UpdateEquipmentDto equipment = new UpdateEquipmentDto(id, name, typeId, facilityId, status, maxCapacity, version);

        assertEquals(1L, equipment.getId());
        assertEquals("equipment1", equipment.getName());
        assertEquals(2L, equipment.getTypeId());
        assertEquals(3L, equipment.getFacilityId());
        assertEquals(EquipmentStatus.ACTIVE, equipment.getStatus());
        assertEquals(new QuantityDto("L", new BigDecimal("100.0")), equipment.getMaxCapacity());
        assertEquals(1, equipment.getVersion());
    }

    @Test
    public void testGetSetName() {
        updateEquipmentDto.setName("testName");
        assertEquals("testName", updateEquipmentDto.getName());
    }

    @Test
    public void testGetSetTypeId() {
        updateEquipmentDto.setTypeId(2L);
        assertEquals(2L, updateEquipmentDto.getTypeId());
    }

    @Test
    public void testGetFacilityId() {
        updateEquipmentDto.setFacilityId(1L);
        assertEquals(1L, updateEquipmentDto.getFacilityId());
    }

    @Test
    public void testGetSetStatus() {
        updateEquipmentDto.setStatus(EquipmentStatus.ACTIVE);
        assertEquals(EquipmentStatus.ACTIVE, updateEquipmentDto.getStatus());
    }

    @Test
    public void testGetSetMaxCapacity() {
        updateEquipmentDto.setMaxCapacity(new QuantityDto("L", new BigDecimal("100.0")));
        assertEquals(new QuantityDto("L", new BigDecimal("100.0")), updateEquipmentDto.getMaxCapacity());
    }

    @Test
    public void testGetSetVersion() {
        updateEquipmentDto.setVersion(1);
        assertEquals(1, updateEquipmentDto.getVersion());
    }
}
