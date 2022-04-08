package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateBrewDtoTest {
    private UpdateBrewDto updateBrewDto;

    @BeforeEach
    public void init() {
        updateBrewDto = new UpdateBrewDto();
    }

    @Test
    public void testConstructor() {
        String name = "testName";
        String description = "testDesc";
        String batchId = "2";
        Long productId = 4L;
        Long parentBrewId = 3L;
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        Long assignedToUserId = 7L;
        Long ownedByUserId = 8L;
        Integer version = 1;

        UpdateBrewDto updateBrewDto = new UpdateBrewDto(name, description, batchId, productId, parentBrewId, startedAt, endedAt, assignedToUserId, ownedByUserId, version);

        assertEquals("testName", updateBrewDto.getName());
        assertEquals("testDesc", updateBrewDto.getDescription());
        assertEquals("2", updateBrewDto.getBatchId());
        assertEquals(4L, updateBrewDto.getProductId());
        assertEquals(3L, updateBrewDto.getParentBrewId());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewDto.getStartedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewDto.getEndedAt());
        assertEquals(7L, updateBrewDto.getAssignedToUserId());
        assertEquals(8L, updateBrewDto.getOwnedByUserId());
        assertEquals(1, updateBrewDto.getVersion());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        updateBrewDto.setName(name);
        assertEquals(name, updateBrewDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        updateBrewDto.setDescription(description);
        assertEquals(description, updateBrewDto.getDescription());
    }

    @Test
    public void testGetSetBatchId() {
        updateBrewDto.setBatchId("2");
        assertEquals("2", updateBrewDto.getBatchId());
    }

    @Test
    public void testGetProductId() {
        Long productId = 4L;
        updateBrewDto.setProductId(productId);
        assertEquals(4L, updateBrewDto.getProductId());
    }

    @Test
    public void testGetSetParentBrew() {
        Long parentBrewId = 3L;
        updateBrewDto.setParentBrewId(parentBrewId);
        assertEquals(parentBrewId, updateBrewDto.getParentBrewId());
    }

    @Test
    public void testGetSetStartedAt() {
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        updateBrewDto.setStartedAt(startedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewDto.getStartedAt());
    }

    @Test
    public void testGetSetEndedAt() {
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        updateBrewDto.setEndedAt(endedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), updateBrewDto.getEndedAt());
    }

    @Test
    public void testGetSetAssignedtoUserId() {
        updateBrewDto.setAssignedToUserId(7L);
        assertEquals(7L, updateBrewDto.getAssignedToUserId());
    }

    @Test
    public void testGetSetOwnedByUserId() {
        updateBrewDto.setOwnedByUserId(8L);
        assertEquals(8L, updateBrewDto.getOwnedByUserId());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateBrewDto.setVersion(version);
        assertEquals(version, updateBrewDto.getVersion());
    }
}
