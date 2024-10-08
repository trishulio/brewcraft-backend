package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.StorageType;

public class StorageDtoTest {
    private StorageDto storageDto;

    @BeforeEach
    public void init() {
        storageDto = new StorageDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        FacilityDto facilityDto = new FacilityDto();
        String name = "storage1";
        StorageType type = StorageType.GENERAL;
        Integer version = 1;

        StorageDto storageDto = new StorageDto(id, facilityDto, name, type, version);

        assertSame(id, storageDto.getId());
        assertSame(facilityDto, storageDto.getFacility());
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
    public void testGetSetFacility() {
        FacilityDto facilityDto = new FacilityDto();
        storageDto.setFacility(facilityDto);
        assertSame(facilityDto, storageDto.getFacility());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        storageDto.setName(name);
        assertSame(name, storageDto.getName());
    }

    @Test
    public void testGetSetType() {
        StorageType type = StorageType.GENERAL;
        storageDto.setType(type);
        assertSame(type, storageDto.getType());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        storageDto.setVersion(version);
        assertSame(version, storageDto.getVersion());
    }
}
