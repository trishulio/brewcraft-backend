package io.company.brewcraft.model;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentTest {

    private Equipment equipment;

    @BeforeEach
    public void init() {
        equipment = new Equipment();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        Facility facility = new Facility();
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        String status = "status";
        QuantityEntity maxCapacity = new QuantityEntity();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Equipment equipment = new Equipment(id, facility, name, type, status, maxCapacity, created, lastUpdated, version);
        
        assertSame(id, equipment.getId());
        assertSame(facility, equipment.getFacility());
        assertSame(name, equipment.getName());
        assertSame(type, equipment.getType());
        assertSame(status, equipment.getStatus());
        assertSame(maxCapacity, equipment.getMaxCapacity());
        assertSame(created, equipment.getCreated());
        assertSame(lastUpdated, equipment.getLastUpdated());
        assertSame(version, equipment.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        equipment.setId(id);
        assertSame(id, equipment.getId());
    }
    
    @Test
    public void testGetSetFacility() {
        Facility facility = new Facility();
        equipment.setFacility(facility);
        assertSame(facility, equipment.getFacility());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        equipment.setName(name);
        assertSame(name, equipment.getName());
    }
    
    @Test
    public void testGetSetType() {
        EquipmentType type = EquipmentType.BARREL;
        equipment.setType(type);
        assertSame(type, equipment.getType());
    }
    
    @Test
    public void testGetSetStatus() {
        String status = "testStatus";
        equipment.setStatus(status);
        assertSame(status, equipment.getStatus());
    }
    
    @Test
    public void testGetSetMaxCapacity() {
        QuantityEntity maxCapacity = new QuantityEntity();
        equipment.setMaxCapacity(maxCapacity);
        assertSame(maxCapacity, equipment.getMaxCapacity());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        equipment.setVersion(version);
        assertSame(version, equipment.getVersion());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        equipment.setCreated(created);
        assertSame(created, equipment.getCreated());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        equipment.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, equipment.getLastUpdated());
    }
}
