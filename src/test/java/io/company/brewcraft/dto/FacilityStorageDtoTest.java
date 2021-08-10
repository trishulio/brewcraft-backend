package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.StorageType;

public class FacilityStorageDtoTest {

    private FacilityStorageDto facilityStorageDto;

    @BeforeEach
    public void init() {
        facilityStorageDto = new FacilityStorageDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "storage1";
        StorageType type = StorageType.GENERAL;
        int version = 1;

        FacilityStorageDto facilityStorageDto = new FacilityStorageDto(id, name, type, version);

        assertSame(id, facilityStorageDto.getId());
        assertSame(name, facilityStorageDto.getName());
        assertSame(type, facilityStorageDto.getType());
        assertSame(version, facilityStorageDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        facilityStorageDto.setId(id);
        assertSame(id, facilityStorageDto.getId());
    }

    @Test
    public void testGetSetName() {
        facilityStorageDto.setName("testName");
        assertSame("testName", facilityStorageDto.getName());
    }

    @Test
    public void testGetSetType() {
        facilityStorageDto.setType(StorageType.GENERAL);
        assertSame(StorageType.GENERAL, facilityStorageDto.getType());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        facilityStorageDto.setVersion(version);
        assertSame(version, facilityStorageDto.getVersion());
    }
}
