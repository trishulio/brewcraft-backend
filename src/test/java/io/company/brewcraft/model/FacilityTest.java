package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FacilityTest {

    private Facility facility;

    @BeforeEach
    public void init() {
        facility = new Facility();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        FacilityAddress address = new FacilityAddress();
        List<Equipment> equipment = new ArrayList<Equipment>();
        List<Storage> storages = new ArrayList<Storage>();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Facility facility = new Facility(id, name, address, equipment, storages, created, lastUpdated, version);
        
        assertSame(id, facility.getId());
        assertSame(name, facility.getName());
        assertSame(address, facility.getAddress());
        assertSame(equipment, facility.getEquipment());
        assertSame(storages, facility.getStorages());
        assertSame(created, facility.getCreated());
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
        facility.setCreated(created);
        assertSame(created, facility.getCreated());
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
