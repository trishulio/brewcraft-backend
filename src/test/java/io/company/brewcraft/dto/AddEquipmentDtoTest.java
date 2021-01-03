package io.company.brewcraft.dto;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;

public class AddEquipmentDtoTest {

    private AddEquipmentDto addEquipmentDto;

    @BeforeEach
    public void init() {
        addEquipmentDto = new AddEquipmentDto();
    }
    
    @Test
    public void testConstructor() {
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));

        AddEquipmentDto equipment = new AddEquipmentDto(name, type, status, maxCapacity);
        
        assertSame(name, equipment.getName());
        assertSame(type, equipment.getType());
        assertSame(status, equipment.getStatus());
        assertSame(maxCapacity, equipment.getMaxCapacity());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        addEquipmentDto.setName(name);
        assertSame(name, addEquipmentDto.getName());
    }
    
    @Test
    public void testGetSetType() {
        EquipmentType type = EquipmentType.FERMENTER;
        addEquipmentDto.setType(type);
        assertSame(type, addEquipmentDto.getType());
    }
    
    @Test
    public void testGetSetStatus() {
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        addEquipmentDto.setStatus(status);
        assertSame(status, addEquipmentDto.getStatus());
    }
    
    @Test
    public void testGetSetMaxCapacity() {
        QuantityDto maxCapacity = new QuantityDto("L", new BigDecimal("100.0"));
        addEquipmentDto.setMaxCapacity(maxCapacity);
        assertSame(maxCapacity, addEquipmentDto.getMaxCapacity());
    }
}
