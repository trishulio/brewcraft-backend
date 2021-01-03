package io.company.brewcraft.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateFacilityDtoTest {

    private UpdateFacilityDto updateFacilityDto;

    @BeforeEach
    public void init() {
        updateFacilityDto = new UpdateFacilityDto();
    }
    
    @Test
    public void testConstructor() {
        String name = "testName";
        AddressDto address = new AddressDto();
        List<FacilityEquipmentDto> equipment = new ArrayList<FacilityEquipmentDto>();
        List<FacilityStorageDto> storages = new ArrayList<FacilityStorageDto>();
        int version = 1;

        UpdateFacilityDto updateFacilityDto = new UpdateFacilityDto(name, address, equipment, storages, version);
        
        assertSame(name, updateFacilityDto.getName());
        assertSame(address, updateFacilityDto.getAddress());
        assertSame(equipment, updateFacilityDto.getEquipment());
        assertSame(storages, updateFacilityDto.getStorages());
        assertSame(version, updateFacilityDto.getVersion());        
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        updateFacilityDto.setName(name);
        assertSame(name, updateFacilityDto.getName());
    }
    
    @Test
    public void testGetSetAddress() {
        AddressDto address = new AddressDto();
        updateFacilityDto.setAddress(address);
        assertSame(address, updateFacilityDto.getAddress());
    }
    
    @Test
    public void testGetSetEquipment() {
        List<FacilityEquipmentDto> equipment = new ArrayList<FacilityEquipmentDto>();
        updateFacilityDto.setEquipment(equipment);
        assertSame(equipment, updateFacilityDto.getEquipment());
    }
    
    @Test
    public void testGetSetStorages() {
        List<FacilityStorageDto> storages = new ArrayList<FacilityStorageDto>();
        updateFacilityDto.setStorages(storages);
        assertSame(storages, updateFacilityDto.getStorages());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateFacilityDto.setVersion(version);
        assertSame(version, updateFacilityDto.getVersion());
    }
}
