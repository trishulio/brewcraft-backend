package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;
import javax.measure.Unit;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;
import tec.uom.se.unit.Units;

public class EquipmentTest {
    private Equipment equipment;

    @BeforeEach
    public void init() {
        equipment = new Equipment();
    }

    @Test
    public void testIdArgConstructor() {
        equipment = new Equipment(1L);

        assertEquals(1L, equipment.getId());
    }

    @Test
    public void testAllArgConstructor() {
        Long id = 1L;
        Facility facility = new Facility();
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        BigDecimal maxCapacityValue = BigDecimal.valueOf(100.0);
        Unit<?> maxCapacityUnit = SupportedUnits.LITRE;
        Unit<?> maxcapacityDisplayUnit = SupportedUnits.LITRE;
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        Equipment equipment = new Equipment(id, facility, name, type, status, maxCapacityValue, maxCapacityUnit, maxcapacityDisplayUnit, created, lastUpdated, version);

        equipment.getMaxCapacity();

        assertEquals(1L, equipment.getId());
        assertEquals(new Facility(), equipment.getFacility());
        assertEquals("equipment1", equipment.getName());
        assertEquals(EquipmentType.BARREL, equipment.getType());
        assertEquals(EquipmentStatus.ACTIVE, equipment.getStatus());
        assertEquals(BigDecimal.valueOf(100.0), equipment.getMaxCapacityValue());
        assertEquals(SupportedUnits.LITRE, equipment.getMaxCapacityUnit());
        assertEquals(SupportedUnits.LITRE, equipment.getMaxCapacityDisplayUnit());
        assertEquals(Quantities.getQuantity(BigDecimal.valueOf(100.0), SupportedUnits.LITRE), equipment.getMaxCapacity());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getLastUpdated());
        assertEquals(1, equipment.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        equipment.setId(id);
        assertEquals(1L, equipment.getId());
    }

    @Test
    public void testGetSetFacility() {
        Facility facility = new Facility();
        equipment.setFacility(facility);
        assertEquals(new Facility(), equipment.getFacility());
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
        Quantity<?> maxCapacity = Quantities.getQuantity(BigDecimal.valueOf(100.0), SupportedUnits.LITRE);
        equipment.setMaxCapacity(maxCapacity);
        assertEquals(Quantities.getQuantity(BigDecimal.valueOf(100.0), SupportedUnits.LITRE), equipment.getMaxCapacity());
    }

    @Test
    public void testGetSetMaxCapacityUnit() {
        Unit<?> unit = SupportedUnits.LITRE;
        equipment.setMaxCapacityUnit(unit);
        assertEquals(SupportedUnits.LITRE, equipment.getMaxCapacityUnit());
    }

    @Test
    public void testGetSetMaxCapacityDisplayUnit() {
        Unit<?> displayUnit = SupportedUnits.LITRE;
        equipment.setMaxCapacityDisplayUnit(displayUnit);
        assertEquals(SupportedUnits.LITRE, equipment.getMaxCapacityDisplayUnit());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        equipment.setVersion(version);
        assertEquals(1, equipment.getVersion());
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

    @Test
    public void testSetMaxCapacity_throwsWhenUnitIsInvalid() {
        Equipment testEquipment = new Equipment();

        assertThrows(IllegalArgumentException.class, () -> {
            testEquipment.setMaxCapacity(Quantities.getQuantity(100, SupportedUnits.EACH));
        });
    }

    @Test
    public void testSetDisplayUnit_throwsWhenUnitIsInvalid() {
        Equipment testEquipment = new Equipment();

        assertThrows(IllegalArgumentException.class, () -> {
            testEquipment.setMaxCapacityDisplayUnit(Units.AMPERE);
        });
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Facility facility = new Facility();
        String name = "equipment1";
        EquipmentType type = EquipmentType.BARREL;
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        BigDecimal maxCapacityValue = BigDecimal.valueOf(100.0);
        Unit<?> maxCapacityUnit = SupportedUnits.LITRE;
        Unit<?> maxcapacityDisplayUnit = SupportedUnits.LITRE;
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        Equipment equipment = new Equipment(id, facility, name, type, status, maxCapacityValue, maxCapacityUnit, maxcapacityDisplayUnit, created, lastUpdated, version);

        final String json = "{\"id\":1,\"facility\":{\"id\":null,\"name\":null,\"address\":null,\"phoneNumber\":null,\"faxNumber\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"name\":\"equipment1\",\"type\":\"Barrel\",\"status\":\"Active\",\"maxCapacityValue\":100.0,\"maxCapacityUnit\":{\"symbol\":\"l\"},\"maxCapacityDisplayUnit\":{\"symbol\":\"l\"},\"createdAt\":\"2020-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1,\"maxCapacity\":{\"symbol\":\"l\",\"value\":100},\"maxCapacityInDisplayUnit\":{\"symbol\":\"l\",\"value\":100}}";
        JSONAssert.assertEquals(json, equipment.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
