package io.company.brewcraft.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FacilityDtoTest {

    private FacilityDto facilityDto;

    @BeforeEach
    public void init() {
        facilityDto = new FacilityDto();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        AddressDto address = new AddressDto();
        List<FacilityEquipmentDto> equipment = new ArrayList<FacilityEquipmentDto>();
        List<FacilityStorageDto> storages = new ArrayList<FacilityStorageDto>();
        int version = 1;

        FacilityDto facilityDto = new FacilityDto(id, name, address, equipment, storages, version);
        
        assertSame(id, facilityDto.getId());
        assertSame(name, facilityDto.getName());
        assertSame(address, facilityDto.getAddress());
        assertSame(equipment, facilityDto.getEquipment());
        assertSame(storages, facilityDto.getStorages());
        assertSame(version, facilityDto.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        facilityDto.setId(id);
        assertSame(id, facilityDto.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        facilityDto.setName(name);
        assertSame(name, facilityDto.getName());
    }
    
    @Test
    public void testGetSetAddress() {
        AddressDto address = new AddressDto();
        facilityDto.setAddress(address);
        assertSame(address, facilityDto.getAddress());
    }
    
    @Test
    public void testGetSetEquipment() {
        List<FacilityEquipmentDto> equipment = new ArrayList<FacilityEquipmentDto>();
        facilityDto.setEquipment(equipment);
        assertSame(equipment, facilityDto.getEquipment());
    }
    
    @Test
    public void testGetSetStorages() {
        List<FacilityStorageDto> storages = new ArrayList<FacilityStorageDto>();
        facilityDto.setStorages(storages);
        assertSame(storages, facilityDto.getStorages());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        facilityDto.setVersion(version);
        assertSame(version, facilityDto.getVersion());
    }
}
