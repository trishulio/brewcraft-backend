package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

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
        String phoneNumber = "6045555555";
        String faxNumber = "6045555555";

        AddFacilityDto addFacilityDto = new AddFacilityDto(name, address, phoneNumber, faxNumber);

        assertSame(name, addFacilityDto.getName());
        assertSame(address, addFacilityDto.getAddress());
        assertSame(phoneNumber, addFacilityDto.getPhoneNumber());
        assertSame(faxNumber, addFacilityDto.getFaxNumber());
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
    public void testGetSetPhoneNumber() {
        String phoneNumber = "6045555555";
        addFacilityDto.setPhoneNumber(phoneNumber);
        assertSame(phoneNumber, addFacilityDto.getPhoneNumber());
    }

    @Test
    public void testGetSetFaxNumber() {
        String faxNumber = "6045555555";
        addFacilityDto.setFaxNumber(faxNumber);
        assertSame(faxNumber, addFacilityDto.getFaxNumber());
    }
}
