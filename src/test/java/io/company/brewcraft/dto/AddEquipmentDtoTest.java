package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentStatus;

public class AddEquipmentDtoTest {
    private AddEquipmentDto addEquipmentDto;

    @BeforeEach
    public void init() {
        addEquipmentDto = new AddEquipmentDto();
    }

    @Test
    public void testConstructor() {
        String name = "equipment1";
        Long typeId = 2L;
        Long facilityId = 3L;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));

        AddEquipmentDto equipment = new AddEquipmentDto(name, typeId, facilityId, status, maxCapacity);

        assertEquals("equipment1", equipment.getName());
        assertEquals(2L, equipment.getTypeId());
        assertEquals(3L, equipment.getFacilityId());
        assertEquals(EquipmentStatus.ACTIVE, equipment.getStatus());
        assertEquals(new QuantityDto("L", new BigDecimal("100.0")), equipment.getMaxCapacity());
    }

    @Test
    public void testGetSetName() {
        addEquipmentDto.setName("testName");
        assertEquals("testName", addEquipmentDto.getName());
    }

    @Test
    public void testGetSetTypeId() {
        addEquipmentDto.setTypeId(2L);
        assertEquals(2L, addEquipmentDto.getTypeId());
    }

    @Test
    public void testGetFacilityId() {
        addEquipmentDto.setFacilityId(1L);
        assertEquals(1L, addEquipmentDto.getFacilityId());
    }

    @Test
    public void testGetSetStatus() {
        addEquipmentDto.setStatus(EquipmentStatus.ACTIVE);
        assertEquals(EquipmentStatus.ACTIVE, addEquipmentDto.getStatus());
    }

    @Test
    public void testGetSetMaxCapacity() {
        addEquipmentDto.setMaxCapacity(new QuantityDto("L", new BigDecimal("100.0")));
        assertEquals(new QuantityDto("L", new BigDecimal("100.0")), addEquipmentDto.getMaxCapacity());
    }
}
