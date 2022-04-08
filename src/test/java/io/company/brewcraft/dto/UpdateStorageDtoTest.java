package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.StorageType;

public class UpdateStorageDtoTest {
    private UpdateStorageDto updateStorageDto;

    @BeforeEach
    public void init() {
        updateStorageDto = new UpdateStorageDto();
    }

    @Test
    public void testConstructor() {
        String name = "storage1";
        StorageType type = StorageType.GENERAL;
        Integer version = 1;

        UpdateStorageDto updateStorageDto = new UpdateStorageDto(name, type, version);

        assertSame(name, updateStorageDto.getName());
        assertSame(type, updateStorageDto.getType());
        assertSame(version, updateStorageDto.getVersion());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        updateStorageDto.setName(name);
        assertSame(name, updateStorageDto.getName());
    }

    @Test
    public void testGetSetType() {
        StorageType type = StorageType.GENERAL;
        updateStorageDto.setType(type);
        assertSame(type, updateStorageDto.getType());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateStorageDto.setVersion(version);
        assertSame(version, updateStorageDto.getVersion());
    }
}
