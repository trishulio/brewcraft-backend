package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
        String phoneNumber = "6045555555";
        String faxNumber = "6045555555";
        int version = 1;

        FacilityDto facilityDto = new FacilityDto(id, name, address, phoneNumber, faxNumber, version);

        assertSame(id, facilityDto.getId());
        assertSame(name, facilityDto.getName());
        assertSame(address, facilityDto.getAddress());
        assertSame(phoneNumber, facilityDto.getPhoneNumber());
        assertSame(faxNumber, facilityDto.getFaxNumber());
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
    public void testGetSetVersion() {
        Integer version = 1;
        facilityDto.setVersion(version);
        assertSame(version, facilityDto.getVersion());
    }
}
