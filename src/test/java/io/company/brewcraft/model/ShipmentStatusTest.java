package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

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
        status = new ShipmentStatus("FINAL");
        assertEquals("FINAL", status.getId());
    }

    @Test
    public void testAccessId_AccessesName() {
        assertEquals(ShipmentStatus.DEFAULT_STATUS_NAME, status.getId());

        status.setId("HELLO");
        assertEquals("HELLO", status.getId());
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
}
