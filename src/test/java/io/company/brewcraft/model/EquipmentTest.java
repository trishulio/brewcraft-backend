package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

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
        Facility facility = new Facility(1L);
        String name = "equipment1";
        EquipmentType type = new EquipmentType(2L);
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        Quantity<?> maxCapacity = Quantities.getQuantity("1000 kg");
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        Equipment equipment = new Equipment(id, facility, name, type, status, maxCapacity, created, lastUpdated, version);

        equipment.getMaxCapacity();

        assertEquals(1L, equipment.getId());
        assertEquals(new Facility(1L), equipment.getFacility());
        assertEquals("equipment1", equipment.getName());
        assertEquals(new EquipmentType(2L), equipment.getType());
        assertEquals(EquipmentStatus.ACTIVE, equipment.getStatus());
        assertEquals(maxCapacity, equipment.getMaxCapacity());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getLastUpdated());
        assertEquals(1, equipment.getVersion());
    }

    @Test
    public void testGetSetId() {
        equipment.setId(1L);
        assertEquals(1L, equipment.getId());
    }

    @Test
    public void testGetSetFacility() {
        equipment.setFacility(new Facility(1L));
        assertEquals(new Facility(1L), equipment.getFacility());
    }

    @Test
    public void testGetSetName() {
        equipment.setName("testName");
        assertEquals("testName", equipment.getName());
    }

    @Test
    public void testGetSetType() {
        equipment.setType(new EquipmentType(2L));
        assertEquals(new EquipmentType(2L), equipment.getType());
    }

    @Test
    public void testGetSetStatus() {
        equipment.setStatus(EquipmentStatus.ACTIVE);
        assertEquals(EquipmentStatus.ACTIVE, equipment.getStatus());
    }

    @Test
    public void testGetSetMaxCapacity() {
        equipment.setMaxCapacity(Quantities.getQuantity(BigDecimal.valueOf(100.0), SupportedUnits.LITRE));
        assertEquals(Quantities.getQuantity(BigDecimal.valueOf(100.0), SupportedUnits.LITRE), equipment.getMaxCapacity());
    }

    @Test
    public void testGetSetVersion() {
        equipment.setVersion(1);
        assertEquals(1, equipment.getVersion());
    }

    @Test
    public void testGetSetCreated() {
        equipment.setCreatedAt(LocalDateTime.of(2020, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        equipment.setLastUpdated(LocalDateTime.of(2020, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), equipment.getLastUpdated());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(equipment.getMaxCapacity());
        equipment.setMaxCapacity(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), equipment.getMaxCapacity());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Facility facility = new Facility(1L);
        String name = "equipment1";
        EquipmentType type = new EquipmentType(2L);
        EquipmentStatus status = EquipmentStatus.ACTIVE;
        Quantity<?> maxCapacity = Quantities.getQuantity("1000 kg");
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        Equipment equipment = new Equipment(id, facility, name, type, status, maxCapacity, created, lastUpdated, version);

        final String json = "{\"id\":1,\"facility\":{\"id\":1,\"name\":null,\"address\":null,\"phoneNumber\":null,\"faxNumber\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"name\":\"equipment1\",\"type\":{\"id\":2,\"name\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"status\":\"Active\",\"maxCapacity\":{\"symbol\":\"kg\",\"value\":1000},\"createdAt\":\"2020-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";

        JSONAssert.assertEquals(json, equipment.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
