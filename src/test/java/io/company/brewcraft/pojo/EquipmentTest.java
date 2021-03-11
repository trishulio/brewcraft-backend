package io.company.brewcraft.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Volume;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import io.company.brewcraft.model.EquipmentStatus;
import io.company.brewcraft.model.EquipmentType;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import io.company.brewcraft.utils.SupportedUnits;
import tec.units.ri.quantity.Quantities;

public class EquipmentTest {

    private Equipment equipment;
    
    public static final QuantityUnitMapper UnitMapper = Mappers.getMapper(QuantityUnitMapper.class);

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
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        Quantity<Volume> maxCapacity = (Quantity<Volume>) Quantities.getQuantity(new BigDecimal("100"), UnitMapper.fromSymbol("hl") );
        Unit<?> displayUnit = SupportedUnits.LITRE;
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Equipment equipment = new Equipment(id, facility, name, type, status, maxCapacity, displayUnit, created, lastUpdated, version);
        
        assertSame(id, equipment.getId());
        assertSame(facility, equipment.getFacility());
        assertSame(name, equipment.getName());
        assertSame(type, equipment.getType());
        assertSame(status, equipment.getStatus());
        assertSame(maxCapacity, equipment.getMaxCapacity());
        assertSame(displayUnit, equipment.getDisplayUnit());
        assertSame(created, equipment.getCreatedAt());
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
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        equipment.setStatus(status);
        assertSame(status, equipment.getStatus());
    }
    
    @Test
    public void testGetSetMaxCapacity() {
        Quantity<Volume> maxCapacity = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE);
        equipment.setMaxCapacity(maxCapacity);
        assertSame(maxCapacity, equipment.getMaxCapacity());
    }
    
    @Test
    public void testGetSetDisplayUnit() {
        Unit<?> displayUnit = SupportedUnits.LITRE;
        equipment.setDisplayUnit(displayUnit);
        assertSame(displayUnit, equipment.getDisplayUnit());
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
        equipment.setCreatedAt(created);
        assertSame(created, equipment.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        equipment.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, equipment.getLastUpdated());
    }
    
    @Test
    public void testGetMaxCapacityInDisplayUnit() {
        
    }
}
