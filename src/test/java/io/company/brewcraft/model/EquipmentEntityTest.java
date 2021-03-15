package io.company.brewcraft.model;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentEntityTest {

    private EquipmentEntity equipment;

    @BeforeEach
    public void init() {
        equipment = new EquipmentEntity();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        FacilityEntity facility = new FacilityEntity();
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        QuantityEntity maxCapacity = new QuantityEntity();
        UnitEntity displayUnit = new UnitEntity();
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        EquipmentEntity equipment = new EquipmentEntity(id, facility, name, type, status, maxCapacity, displayUnit, created, lastUpdated, version);
        
        assertEquals((Long) 1L, equipment.getId());
        assertEquals(new FacilityEntity(), equipment.getFacility());
        assertEquals("equipment1", equipment.getName());
        assertEquals(EquipmentType.BARREL, equipment.getType());
        assertEquals(EquipmentStatus.ACTIVE, equipment.getStatus());
        assertEquals(new QuantityEntity(), equipment.getMaxCapacity());
        assertEquals(new UnitEntity(), equipment.getDisplayUnit());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getLastUpdated());
        assertEquals((Integer)1, equipment.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        equipment.setId(id);
        assertEquals((Long) 1L, equipment.getId());
    }
    
    @Test
    public void testGetSetFacility() {
        FacilityEntity facility = new FacilityEntity();
        equipment.setFacility(facility);
        assertEquals(new FacilityEntity(), equipment.getFacility());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        equipment.setName(name);
        assertEquals("testName", equipment.getName());
    }
    
    @Test
    public void testGetSetType() {
        EquipmentType type = EquipmentType.BARREL;
        equipment.setType(type);
        assertEquals(EquipmentType.BARREL, equipment.getType());
    }
    
    @Test
    public void testGetSetStatus() {
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        equipment.setStatus(status);
        assertEquals(EquipmentStatus.ACTIVE, equipment.getStatus());
    }
    
    @Test
    public void testGetSetMaxCapacity() {
        QuantityEntity maxCapacity = new QuantityEntity();
        equipment.setMaxCapacity(maxCapacity);
        assertEquals(new QuantityEntity(), equipment.getMaxCapacity());
    }
    
    @Test
    public void testGetSetDisplayUnit() {
        UnitEntity displayUnit = new UnitEntity();
        equipment.setDisplayUnit(displayUnit);
        assertEquals(new UnitEntity(), equipment.getDisplayUnit());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        equipment.setVersion(version);
        assertEquals((Integer)1, equipment.getVersion());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        equipment.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        equipment.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getLastUpdated());
    }
}
