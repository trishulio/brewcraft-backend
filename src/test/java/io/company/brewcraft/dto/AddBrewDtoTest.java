package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddBrewDtoTest {

    private AddBrewDto addBrewDto;

    @BeforeEach
    public void init() {
        addBrewDto = new AddBrewDto();
    }

    @Test
    public void testConstructor() {
        String name = "testName";
        String description = "testDesc";
        Long batchId = 2L;
        Long productId = 4L;
        Long parentBrewId = 3L;
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);

        AddBrewDto addBrewDto = new AddBrewDto(name, description, batchId, productId, parentBrewId, startedAt, endedAt);

        assertEquals("testName", addBrewDto.getName());
        assertEquals("testDesc", addBrewDto.getDescription());
        assertEquals(2L, addBrewDto.getBatchId());
        assertEquals(4L, addBrewDto.getProductId());
        assertEquals(3L, addBrewDto.getParentBrewId());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewDto.getStartedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewDto.getEndedAt());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        addBrewDto.setName(name);
        assertEquals(name, addBrewDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        addBrewDto.setDescription(description);
        assertEquals(description, addBrewDto.getDescription());
    }

    @Test
    public void testGetSetBatchId() {
        Long batchId = 2L;
        addBrewDto.setBatchId(batchId);
        assertEquals(2L, addBrewDto.getBatchId());
    }

    @Test
    public void testGetProduct() {
        Long productId = 4L;
        addBrewDto.setProductId(productId);
        assertEquals(4L, addBrewDto.getProductId());
    }

    @Test
    public void testGetSetParentBrew() {
        Long parentBrewId = 3L;
        addBrewDto.setParentBrewId(parentBrewId);
        assertEquals(parentBrewId, addBrewDto.getParentBrewId());
    }

    @Test
    public void testGetSetStartedAt() {
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        addBrewDto.setStartedAt(startedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewDto.getStartedAt());
    }

    @Test
    public void testGetSetEndedAt() {
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        addBrewDto.setEndedAt(endedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addBrewDto.getEndedAt());
    }
}
