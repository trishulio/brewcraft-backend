package io.company.brewcraft.dto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;

public class UpdateEquipmentDtoTest {

    private UpdateEquipmentDto updateEquipmentDto;

    @BeforeEach
    public void init() {
        updateEquipmentDto = new UpdateEquipmentDto();
    }
    
    @Test
    public void testConstructor() {
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        int version = 1;

        UpdateEquipmentDto equipment = new UpdateEquipmentDto(name, type, status, maxCapacity, version);
        
        assertSame(name, equipment.getName());
        assertSame(type, equipment.getType());
        assertSame(status, equipment.getStatus());
        assertSame(maxCapacity, equipment.getMaxCapacity());
        assertSame(version, equipment.getVersion());        
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        updateEquipmentDto.setName(name);
        assertSame(name, updateEquipmentDto.getName());
    }
    
    @Test
    public void testGetSetType() {
        EquipmentType type = EquipmentType.FERMENTER;
        updateEquipmentDto.setType(type);
        assertSame(type, updateEquipmentDto.getType());
    }
    
    @Test
    public void testGetSetStatus() {
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        updateEquipmentDto.setStatus(status);
        assertSame(status, updateEquipmentDto.getStatus());
    }
    
    @Test
    public void testGetSetMaxCapacity() {
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        updateEquipmentDto.setMaxCapacity(maxCapacity);
        assertSame(maxCapacity, updateEquipmentDto.getMaxCapacity());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateEquipmentDto.setVersion(version);
        assertSame(version, updateEquipmentDto.getVersion());
    }
}
