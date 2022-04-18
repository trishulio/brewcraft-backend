package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentStatus;

public class EquipmentDtoTest {
    private EquipmentDto equipmentDto;

    @BeforeEach
    public void init() {
        equipmentDto = new EquipmentDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        FacilityDto facility = new FacilityDto(1L);
        String name = "equipment1";
        EquipmentTypeDto type = new EquipmentTypeDto(2L);
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        int version = 1;

        EquipmentDto equipmentDto = new EquipmentDto(id, facility, name, type, status, maxCapacity, version);

        assertEquals(id, equipmentDto.getId());
        assertEquals(facility, equipmentDto.getFacility());
        assertEquals(name, equipmentDto.getName());
        assertEquals(type, equipmentDto.getType());
        assertEquals(status, equipmentDto.getStatus());
        assertEquals(maxCapacity, equipmentDto.getMaxCapacity());
        assertEquals(version, equipmentDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        equipmentDto.setId(1L);
        assertEquals(1L, equipmentDto.getId());
    }

    @Test
    public void testGetSetFacility() {
        equipmentDto.setFacility(new FacilityDto(1L));
        assertEquals(new FacilityDto(1L), equipmentDto.getFacility());
    }

    @Test
    public void testGetSetName() {
        equipmentDto.setName("testName");
        assertEquals("testName", equipmentDto.getName());
    }

    @Test
    public void testGetSetType() {
        equipmentDto.setType(new EquipmentTypeDto(2L));
        assertEquals(new EquipmentTypeDto(2L), equipmentDto.getType());
    }

    @Test
    public void testGetSetStatus() {
        equipmentDto.setStatus(EquipmentStatus.ACTIVE);
        assertEquals(EquipmentStatus.ACTIVE, equipmentDto.getStatus());
    }

    @Test
    public void testGetSetMaxCapacity() {
        equipmentDto.setMaxCapacity(new QuantityDto("L", new BigDecimal("100.0")));
        assertEquals(new QuantityDto("L", new BigDecimal("100.0")), equipmentDto.getMaxCapacity());
    }

    @Test
    public void testGetSetVersion() {
        equipmentDto.setVersion(1);
        assertEquals(1, equipmentDto.getVersion());
    }
}
