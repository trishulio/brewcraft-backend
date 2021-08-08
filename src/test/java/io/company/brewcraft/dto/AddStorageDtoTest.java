package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.StorageType;

public class AddStorageDtoTest {

    private AddStorageDto addStorageDto;

    @BeforeEach
    public void init() {
        addStorageDto = new AddStorageDto();
    }
    
    @Test
    public void testConstructor() {
        String name = "storage1";
        StorageType type = StorageType.GENERAL;        

        AddStorageDto addStorageDto = new AddStorageDto(name, type);
        
        assertSame(name, addStorageDto.getName());
        assertSame(type, addStorageDto.getType());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        addStorageDto.setName(name);
        assertSame(name, addStorageDto.getName());
    }
    
    @Test
    public void testGetSetType() {
        StorageType type = StorageType.GENERAL;
        addStorageDto.setType(type);
        assertSame(type, addStorageDto.getType());
    }

}
