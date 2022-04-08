package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class FacilityTest {
    private Facility facility;

    @BeforeEach
    public void init() {
        facility = new Facility();
    }

    @Test
    public void testIdArgConstructor() {
        facility = new Facility(1L);

        assertEquals(1L, facility.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        Long id = 1L;
        String name = "testName";
        FacilityAddress address = new FacilityAddress();
        String phoneNumber = "testPhoneNumber";
        String faxNumber = "testFaxNumber";
        List<Equipment> equipment = new ArrayList<Equipment>();
        List<Storage> storages = new ArrayList<Storage>();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Facility facility = new Facility(id, name, address, phoneNumber, faxNumber, equipment, storages, created, lastUpdated, version);

        assertSame(id, facility.getId());
        assertSame(name, facility.getName());
        assertSame(address, facility.getAddress());
        assertSame(phoneNumber, facility.getPhoneNumber());
        assertSame(faxNumber, facility.getFaxNumber());
        assertSame(equipment, facility.getEquipment());
        assertSame(storages, facility.getStorages());
        assertSame(created, facility.getCreatedAt());
        assertSame(lastUpdated, facility.getLastUpdated());
        assertSame(version, facility.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        facility.setId(id);
        assertSame(id, facility.getId());
    }

    @Test
    public void testGetSetName() {
        facility.setName("testName");
        assertSame("testName", facility.getName());
    }

    @Test
    public void testGetSetAddress() {
        FacilityAddress address = new FacilityAddress();
        facility.setAddress(address);
        assertSame(address, facility.getAddress());
    }

    @Test
    public void testGetSetPhoneNumber() {
        facility.setPhoneNumber("phoneNumber");
        assertSame("phoneNumber", facility.getPhoneNumber());
    }

    @Test
    public void testGetSetFaxNumber() {
        facility.setFaxNumber("faxNumber");
        assertSame("faxNumber", facility.getFaxNumber());
    }

    @Test
    public void testGetSetEquipment() {
        List<Equipment> equipment = new ArrayList<Equipment>();
        facility.setEquipment(equipment);
        assertSame(equipment, facility.getEquipment());
    }

    @Test
    public void testGetSetStorages() {
        List<Storage> storages = new ArrayList<Storage>();
        facility.setStorages(storages);
        assertSame(storages, facility.getStorages());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        facility.setCreatedAt(created);
        assertSame(created, facility.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        facility.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, facility.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        facility.setVersion(version);
        assertSame(version, facility.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        String name = "testName";
        FacilityAddress address = new FacilityAddress();
        String phoneNumber = "testPhoneNumber";
        String faxNumber = "testFaxNumber";
        List<Equipment> equipment = new ArrayList<Equipment>();
        List<Storage> storages = new ArrayList<Storage>();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Facility facility = new Facility(id, name, address, phoneNumber, faxNumber, equipment, storages, created, lastUpdated, version);

        final String json = "{\"id\":1,\"name\":\"testName\",\"address\":{\"addressLine1\":null,\"addressLine2\":null,\"country\":null,\"province\":null,\"city\":null,\"postalCode\":null,\"createdAt\":null,\"lastUpdated\":null,\"id\":null},\"phoneNumber\":\"testPhoneNumber\",\"faxNumber\":\"testFaxNumber\",\"createdAt\":\"2020-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, facility.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
