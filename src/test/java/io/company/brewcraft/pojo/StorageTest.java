package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.StorageType;

public class StorageTest {

    private Storage storage;

    @BeforeEach
    public void init() {
        storage = new Storage();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        Facility facility = new Facility();
        String name = "storage1";
        StorageType type = StorageType.GENERAL;
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Storage storage = new Storage(id, facility, name, type, created, lastUpdated, version);
        
        assertSame(id, storage.getId());
        assertSame(facility, storage.getFacility());
        assertSame(name, storage.getName());
        assertSame(type, storage.getType());
        assertSame(created, storage.getCreatedAt());
        assertSame(lastUpdated, storage.getLastUpdated());
        assertSame(version, storage.getVersion());        
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        storage.setId(id);
        assertSame(id, storage.getId());
    }
    
    @Test
    public void testGetSetFacility() {
        Facility facility = new Facility();
        storage.setFacility(facility);
        assertSame(facility, storage.getFacility());
    }

    @Test
    public void testGetSetName() {
        storage.setName("testName");
        assertSame("testName", storage.getName());
    }
    
    @Test
    public void testGetSetType() {
        storage.setType(StorageType.GENERAL);
        assertSame(StorageType.GENERAL, storage.getType());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        storage.setVersion(version);
        assertSame(version, storage.getVersion());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        storage.setCreatedAt(created);
        assertSame(created, storage.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        storage.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, storage.getLastUpdated());
    }
}
