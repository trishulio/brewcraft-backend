package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;

public class FacilityEquipmentDtoTest {
    private FacilityEquipmentDto facilityEquipmentDtoTest;

    @BeforeEach
    public void init() {
        facilityEquipmentDtoTest = new FacilityEquipmentDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        int version = 1;

        FacilityEquipmentDto facilityEquipmentDtoTest = new FacilityEquipmentDto(id, name, type, status, maxCapacity, version);

        assertSame(id, facilityEquipmentDtoTest.getId());
        assertSame(name, facilityEquipmentDtoTest.getName());
        assertSame(type, facilityEquipmentDtoTest.getType());
        assertSame(status, facilityEquipmentDtoTest.getStatus());
        assertSame(maxCapacity, facilityEquipmentDtoTest.getMaxCapacity());
        assertSame(version, facilityEquipmentDtoTest.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        facilityEquipmentDtoTest.setId(id);
        assertSame(id, facilityEquipmentDtoTest.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        facilityEquipmentDtoTest.setName(name);
        assertSame(name, facilityEquipmentDtoTest.getName());
    }

    @Test
    public void testGetSetType() {
        EquipmentType type = EquipmentType.FERMENTER;
        facilityEquipmentDtoTest.setType(type);
        assertSame(type, facilityEquipmentDtoTest.getType());
    }

    @Test
    public void testGetSetStatus() {
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        facilityEquipmentDtoTest.setStatus(status);
        assertSame(status, facilityEquipmentDtoTest.getStatus());
    }

    @Test
    public void testGetSetMaxCapacity() {
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        facilityEquipmentDtoTest.setMaxCapacity(maxCapacity);
        assertSame(maxCapacity, facilityEquipmentDtoTest.getMaxCapacity());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        facilityEquipmentDtoTest.setVersion(version);
        assertSame(version, facilityEquipmentDtoTest.getVersion());
    }
}
