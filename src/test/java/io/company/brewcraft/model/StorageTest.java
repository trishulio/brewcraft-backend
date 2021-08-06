package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class StorageTest {

    private Storage storage;

    @BeforeEach
    public void init() {
        storage = new Storage();
    }

    @Test
    public void testIdArgConstructor() {
        storage = new Storage(1L);

        assertEquals(1L, storage.getId());
    }

    @Test
    public void testAllArgConstructor() {
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

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Facility facility = new Facility();
        String name = "storage1";
        StorageType type = StorageType.GENERAL;
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Storage storage = new Storage(id, facility, name, type, created, lastUpdated, version);

        final String json = "{\"id\":1,\"facility\":{\"id\":null,\"name\":null,\"address\":null,\"phoneNumber\":null,\"faxNumber\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"name\":\"storage1\",\"type\":\"General\",\"createdAt\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        JSONAssert.assertEquals(json, storage.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
