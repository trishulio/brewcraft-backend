package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.BrewStageStatusDto;
import io.company.brewcraft.model.BrewStageStatus;

public class BrewStageStatusMapperTest {
    private BrewStageStatusMapper brewStageStatusMapper;

    @BeforeEach
    public void init() {
        brewStageStatusMapper = BrewStageStatusMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnPojo_WhenIdIsNotNull() {
        BrewStageStatus status = brewStageStatusMapper.fromDto(1L);
        BrewStageStatus expected = new BrewStageStatus(1L);

        assertEquals(expected, status);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(brewStageStatusMapper.fromDto((Long) null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
    	BrewStageStatus status = brewStageStatusMapper.fromDto(new BrewStageStatusDto(1L));
    	BrewStageStatus expected = new BrewStageStatus(1L);

        assertEquals(expected, status);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(brewStageStatusMapper.fromDto((BrewStageStatusDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
    	BrewStageStatusDto status = brewStageStatusMapper.toDto(new BrewStageStatus(1L));

    	BrewStageStatusDto expected = new BrewStageStatusDto(1L);
        assertEquals(expected, status);
    }
    
    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(brewStageStatusMapper.toDto(null));
    }

}
