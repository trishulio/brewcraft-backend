package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FacilityEntityTest {

    private FacilityEntity facility;

    @BeforeEach
    public void init() {
        facility = new FacilityEntity();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        FacilityAddressEntity address = new FacilityAddressEntity();
        String phoneNumber = "testPhoneNumber";
        String faxNumber = "testFaxNumber";
        List<EquipmentEntity> equipment = new ArrayList<EquipmentEntity>();
        List<StorageEntity> storages = new ArrayList<StorageEntity>();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FacilityEntity facility = new FacilityEntity(id, name, address, phoneNumber, faxNumber, equipment, storages, created, lastUpdated, version);
        
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
        FacilityAddressEntity address = new FacilityAddressEntity();
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
        List<EquipmentEntity> equipment = new ArrayList<EquipmentEntity>();
        facility.setEquipment(equipment);
        assertSame(equipment, facility.getEquipment());
    }
    
    @Test
    public void testGetSetStorages() {
        List<StorageEntity> storages = new ArrayList<StorageEntity>();
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
}
