package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipmentStatusTest {
    private ShipmentStatus status;

    @BeforeEach
    public void init() {
        status = new ShipmentStatus();
    }

    @Test
    public void testIdArgConstructor() {
        status = new ShipmentStatus(99L);
        assertEquals(99L, status.getId());
    }
    
    @Test
    public void testAllArgConstructor() {
        status = new ShipmentStatus(99L, "FINAL", LocalDateTime.of(1999, 12, 12, 0, 0), LocalDateTime.of(2000, 12, 12, 0, 0), 1);
        
        assertEquals(99L, status.getId());
        assertEquals("FINAL", status.getName());
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), status.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), status.getLastUpdated());
        assertEquals(1, status.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(status.getId());
        status.setId(1L);
        assertEquals(1L, status.getId());
    }
    
    @Test
    public void testAccessName() {
        assertNull(status.getName());
        status.setName("STATUS_NAME");
        assertEquals("STATUS_NAME", status.getName());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(status.getCreatedAt());
        status.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), status.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(status.getLastUpdated());
        status.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), status.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(status.getVersion());
        status.setVersion(1);
        assertEquals(1, status.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() {
        status = new ShipmentStatus(99L, "FINAL", LocalDateTime.of(1999, 12, 12, 0, 0), LocalDateTime.of(2000, 12, 12, 0, 0), 1);
        
        final String json = "{\"id\":99,\"name\":\"FINAL\",\"createdAt\":{\"nano\":0,\"year\":1999,\"monthValue\":12,\"dayOfMonth\":12,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"SUNDAY\",\"dayOfYear\":346,\"month\":\"DECEMBER\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2000,\"monthValue\":12,\"dayOfMonth\":12,\"hour\":0,\"minute\":0,\"second\":0,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":347,\"month\":\"DECEMBER\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        assertEquals(json, status.toString());
    }
}
