package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
        String phoneNumber = "6045555555";
        String faxNumber = "6045555555";
        int version = 1;

        UpdateFacilityDto updateFacilityDto = new UpdateFacilityDto(name, address, phoneNumber, faxNumber, version);

        assertSame(name, updateFacilityDto.getName());
        assertSame(address, updateFacilityDto.getAddress());
        assertSame(phoneNumber, updateFacilityDto.getPhoneNumber());
        assertSame(faxNumber, updateFacilityDto.getFaxNumber());
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
    public void testGetSetPhoneNumber() {
        String phoneNumber = "6045555555";
        updateFacilityDto.setPhoneNumber(phoneNumber);
        assertSame(phoneNumber, updateFacilityDto.getPhoneNumber());
    }

    @Test
    public void testGetSetFaxNumber() {
        String faxNumber = "6045555555";
        updateFacilityDto.setFaxNumber(faxNumber);
        assertSame(faxNumber, updateFacilityDto.getFaxNumber());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateFacilityDto.setVersion(version);
        assertSame(version, updateFacilityDto.getVersion());
    }
}
