package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipmentStatusDtoTest {
    private ShipmentStatusDto shipmentStatus;

    @BeforeEach
    public void init() {
        shipmentStatus = new ShipmentStatusDto();
    }

    @Test
    public void testIdArgConstructor() {
        shipmentStatus = new ShipmentStatusDto(99L);
        assertEquals(99L, shipmentStatus.getId());
    }

    @Test
    public void testAllArgConstructor() {
        shipmentStatus = new ShipmentStatusDto(99L, "FINAL", LocalDateTime.of(1999, 12, 12, 0, 0), LocalDateTime.of(2000, 12, 12, 0, 0), 1);

        assertEquals(99L, shipmentStatus.getId());
        assertEquals("FINAL", shipmentStatus.getName());
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), shipmentStatus.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), shipmentStatus.getLastUpdated());
        assertEquals(1, shipmentStatus.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(shipmentStatus.getId());
        shipmentStatus.setId(1L);
        assertEquals(1L, shipmentStatus.getId());
    }

    @Test
    public void testAccessName() {
        assertNull(shipmentStatus.getName());
        shipmentStatus.setName("STATUS_NAME");
        assertEquals("STATUS_NAME", shipmentStatus.getName());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(shipmentStatus.getCreatedAt());
        shipmentStatus.setCreatedAt(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), shipmentStatus.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(shipmentStatus.getLastUpdated());
        shipmentStatus.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), shipmentStatus.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(shipmentStatus.getVersion());
        shipmentStatus.setVersion(1);
        assertEquals(1, shipmentStatus.getVersion());
    }
}
