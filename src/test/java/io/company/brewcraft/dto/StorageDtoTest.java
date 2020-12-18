package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageDtoTest {

    private FacilityStorageDto storageDto;

    @BeforeEach
    public void init() {
        storageDto = new FacilityStorageDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "storage1";
        String type = "type";
        int version = 1;

        FacilityStorageDto storageDto = new FacilityStorageDto(id, name, type, version);
        
        assertSame(id, storageDto.getId());
        assertSame(name, storageDto.getName());
        assertSame(type, storageDto.getType());
        assertSame(version, storageDto.getVersion());        
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        storageDto.setId(id);
        assertSame(id, storageDto.getId());
    }

    @Test
    public void testGetSetName() {
        storageDto.setName("testName");
        assertSame("testName", storageDto.getName());
    }
    
    @Test
    public void testGetSetType() {
        storageDto.setType("testType");
        assertSame("testType", storageDto.getType());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        storageDto.setVersion(version);
        assertSame(version, storageDto.getVersion());
    }
}
