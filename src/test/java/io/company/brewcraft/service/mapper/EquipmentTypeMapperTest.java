package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.EquipmentTypeDto;
import io.company.brewcraft.model.EquipmentType;

public class EquipmentTypeMapperTest {
    private EquipmentTypeMapper mapper;

    @BeforeEach
    public void init() {
        mapper = EquipmentTypeMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnPojo_WhenIdIsNotNull() {
        EquipmentType equipmentType = mapper.fromDto(99l);
        EquipmentType expected = new EquipmentType(99l);

        assertEquals(expected, equipmentType);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(mapper.fromDto((Long) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        EquipmentTypeDto equipmentType = mapper.toDto(new EquipmentType(99l));

        EquipmentTypeDto expected = new EquipmentTypeDto(99l);
        assertEquals(expected, equipmentType);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(mapper.toDto(null));
    }
}
