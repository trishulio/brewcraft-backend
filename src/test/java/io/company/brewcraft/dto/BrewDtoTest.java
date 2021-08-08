package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BrewDtoTest {

    private BrewDto brewDto;

    @BeforeEach
    public void init() {
        brewDto = new BrewDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        Long batchId = 2L;
        ProductDto product = new ProductDto();
        Long parentBrewId = 3L;
        LocalDateTime startedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime endedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        BrewDto brewDto = new BrewDto(id, name, description, batchId, product, parentBrewId, startedAt, endedAt, created, version);
        
        assertEquals(1L, brewDto.getId());
        assertEquals("testName", brewDto.getName());
        assertEquals("testDesc", brewDto.getDescription());
        assertEquals(2L, brewDto.getBatchId());
        assertEquals(new ProductDto(), brewDto.getProduct());
        assertEquals(3L, brewDto.getParentBrewId());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getStartedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), brewDto.getEndedAt());
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
        Long batchId = 2L;
        brewDto.setBatchId(batchId);
        assertEquals(2L, brewDto.getBatchId());
    }
    
    @Test
    public void testGetProduct() {
        ProductDto product = new ProductDto();
        brewDto.setProduct(product);
        assertEquals(new ProductDto(), brewDto.getProduct());
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
