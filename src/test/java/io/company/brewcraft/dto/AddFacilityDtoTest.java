package io.company.brewcraft.dto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddFacilityDtoTest {

    private AddFacilityDto addFacilityDto;

    @BeforeEach
    public void init() {
        addFacilityDto = new AddFacilityDto();
    }
    
    @Test
    public void testConstructor() {
        String name = "testName";
        AddressDto address = new AddressDto();
        List<FacilityEquipmentDto> equipment = new ArrayList<FacilityEquipmentDto>();
        List<FacilityStorageDto> storages = new ArrayList<FacilityStorageDto>();

        AddFacilityDto addFacilityDto = new AddFacilityDto(name, address, equipment, storages);
        
        assertSame(name, addFacilityDto.getName());
        assertSame(address, addFacilityDto.getAddress());
        assertSame(equipment, addFacilityDto.getEquipment());
        assertSame(storages, addFacilityDto.getStorages());
    }
    
    @Test
    public void testGetSetName() {
        String name = "testName";
        addFacilityDto.setName(name);
        assertSame(name, addFacilityDto.getName());
    }
    
    @Test
    public void testGetSetAddress() {
        AddressDto address = new AddressDto();
        addFacilityDto.setAddress(address);
        assertSame(address, addFacilityDto.getAddress());
    }
    
    @Test
    public void testGetSetEquipment() {
        List<FacilityEquipmentDto> equipment = new ArrayList<FacilityEquipmentDto>();
        addFacilityDto.setEquipment(equipment);
        assertSame(equipment, addFacilityDto.getEquipment());
    }
    
    @Test
    public void testGetSetStorages() {
        List<FacilityStorageDto> storages = new ArrayList<FacilityStorageDto>();
        addFacilityDto.setStorages(storages);
        assertSame(storages, addFacilityDto.getStorages());
    }

}
