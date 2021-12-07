package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.BrewTaskDto;
import io.company.brewcraft.model.BrewTask;

public class BrewTaskMapperTest {
    private BrewTaskMapper brewTaskMapper;

    @BeforeEach
    public void init() {
        brewTaskMapper = BrewTaskMapper.INSTANCE;
    }

    @Test
    public void testFromDto_ReturnPojo_WhenIdIsNotNull() {
        BrewTask task = brewTaskMapper.fromDto(1L);
        BrewTask expected = new BrewTask(1L);

        assertEquals(expected, task);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenIdIsNull() {
        assertNull(brewTaskMapper.fromDto((Long) null));
    }

    @Test
    public void testFromDto_ReturnsPojo_WhenDtoIsNotNull() {
        BrewTask task = brewTaskMapper.fromDto(new BrewTaskDto(1L));
        BrewTask expected = new BrewTask(1L);

        assertEquals(expected, task);
    }

    @Test
    public void testFromDto_ReturnsNull_WhenDtoIsNull() {
        assertNull(brewTaskMapper.fromDto((BrewTaskDto) null));
    }

    @Test
    public void testToDto_ReturnsDto_WhenPojoIsNotNull() {
        BrewTaskDto task = brewTaskMapper.toDto(new BrewTask(1L));

        BrewTaskDto expected = new BrewTaskDto(1L);
        assertEquals(expected, task);
    }

    @Test
    public void testToDto_ReturnsNull_WhenPojoIsNull() {
        assertNull(brewTaskMapper.toDto(null));
    }
}
