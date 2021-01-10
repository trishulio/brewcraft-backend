package io.company.brewcraft.dto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;

public class EquipmentDtoTest {

    private EquipmentDto equipmentDto;

    @BeforeEach
    public void init() {
        equipmentDto = new EquipmentDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        FacilityBaseDto facility = new FacilityBaseDto();
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        int version = 1;

        EquipmentDto equipmentDto = new EquipmentDto(id, facility, name, type, status, maxCapacity, version);
        
        assertSame(id, equipmentDto.getId());
        assertSame(facility, equipmentDto.getFacility());
        assertSame(name, equipmentDto.getName());
        assertSame(type, equipmentDto.getType());
        assertSame(status, equipmentDto.getStatus());
        assertSame(maxCapacity, equipmentDto.getMaxCapacity());
        assertSame(version, equipmentDto.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        equipmentDto.setId(id);
        assertSame(id, equipmentDto.getId());
    }
    
    @Test
    public void testGetSetFacility() {
        FacilityBaseDto facility = new FacilityBaseDto();
        equipmentDto.setFacility(facility);
        assertSame(facility, equipmentDto.getFacility());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        equipmentDto.setName(name);
        assertSame(name, equipmentDto.getName());
    }
    
    @Test
    public void testGetSetType() {
        EquipmentType type = EquipmentType.FERMENTER;
        equipmentDto.setType(type);
        assertSame(type, equipmentDto.getType());
    }
    
    @Test
    public void testGetSetStatus() {
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        equipmentDto.setStatus(status);
        assertSame(status, equipmentDto.getStatus());
    }
    
    @Test
    public void testGetSetMaxCapacity() {
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        equipmentDto.setMaxCapacity(maxCapacity);
        assertSame(maxCapacity, equipmentDto.getMaxCapacity());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        equipmentDto.setVersion(version);
        assertSame(version, equipmentDto.getVersion());
    }
}
