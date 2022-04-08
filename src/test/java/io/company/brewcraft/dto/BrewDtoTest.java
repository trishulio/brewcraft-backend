package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.user.UserDto;

public class BrewDtoTest {
    private BrewDto brewDto;

    @BeforeEach
    public void init() {
        brewDto = new BrewDto();
    }

    @Test
    public void testIdConstructor() {
        Long id = 1L;

        BrewDto brewDto = new BrewDto(id);

        assertEquals(1L, brewDto.getId());
        assertNull(brewDto.getName());
        assertNull(brewDto.getDescription());
        assertNull(brewDto.getBatchId());
        assertNull(brewDto.getProduct());
        assertNull(brewDto.getParentBrewId());
        assertNull(brewDto.getStartedAt());
        assertNull(brewDto.getEndedAt());
        assertNull(brewDto.getAssignedTo());
        assertNull(brewDto.getOwnedBy());
        assertNull(brewDto.getCreatedAt());
        assertNull(brewDto.getVersion());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        String batchId = "2";
        ProductDto product = new ProductDto(5L);
        Long parentBrewId = 3L;
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        UserDto assignedTo = new UserDto(7L);
        UserDto ownedBy = new UserDto(8L);
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        BrewDto brewDto = new BrewDto(id, name, description, batchId, product, parentBrewId, startedAt, endedAt, assignedTo, ownedBy, created, version);

        assertEquals(1L, brewDto.getId());
        assertEquals("testName", brewDto.getName());
        assertEquals("testDesc", brewDto.getDescription());
        assertEquals("2", brewDto.getBatchId());
        assertEquals(new ProductDto(5L), brewDto.getProduct());
        assertEquals(3L, brewDto.getParentBrewId());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getStartedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getEndedAt());
        assertEquals(new UserDto(7L), brewDto.getAssignedTo());
        assertEquals(new UserDto(8L), brewDto.getOwnedBy());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getCreatedAt());
        assertEquals(1, brewDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        brewDto.setId(id);
        assertEquals(id, brewDto.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        brewDto.setName(name);
        assertEquals(name, brewDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        brewDto.setDescription(description);
        assertEquals(description, brewDto.getDescription());
    }

    @Test
    public void testGetSetBatchId() {
        brewDto.setBatchId("2");
        assertEquals("2", brewDto.getBatchId());
    }

    @Test
    public void testGetProduct() {
        ProductDto product = new ProductDto(5L);
        brewDto.setProduct(product);
        assertEquals(new ProductDto(5L), brewDto.getProduct());
    }

    @Test
    public void testGetSetParentBrew() {
        Long parentBrewId = 3L;
        brewDto.setParentBrewId(parentBrewId);
        assertEquals(parentBrewId, brewDto.getParentBrewId());
    }

    @Test
    public void testGetSetStartedAt() {
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewDto.setStartedAt(startedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getStartedAt());
    }

    @Test
    public void testGetSetEndedAt() {
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewDto.setEndedAt(endedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getEndedAt());
    }

    @Test
    public void testGetSetAssignedTo() {
        brewDto.setAssignedTo(new UserDto(7L));
        assertEquals(new UserDto(7L), brewDto.getAssignedTo());
    }

    @Test
    public void testGetSetOwnedBy() {
        brewDto.setOwnedBy(new UserDto(8L));
        assertEquals(new UserDto(8L), brewDto.getOwnedBy());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        brewDto.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getCreatedAt());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        brewDto.setVersion(version);
        assertEquals(version, brewDto.getVersion());
    }
}
