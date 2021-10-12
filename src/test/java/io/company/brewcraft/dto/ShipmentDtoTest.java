package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipmentDtoTest {
    private ShipmentDto dto;

    @BeforeEach
    public void init() {
        dto = new ShipmentDto();
    }

    @Test
    public void testIdArgConstructor() {
        dto = new ShipmentDto(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testAllArgConstructor_SetsAllFieldValues() {
        List<MaterialLotDto> items = List.of(new MaterialLotDto(1L));
        dto = new ShipmentDto(1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatusDto(99L),
            LocalDateTime.of(1999, 1, 1, 0, 0),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            LocalDateTime.of(2002, 1, 1, 0, 0),
            items,
            1
        );

        assertEquals(1L, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(new ShipmentStatusDto(99L), dto.getShipmentStatus());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 0, 0), dto.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 0, 0), dto.getLastUpdated());
        assertEquals(List.of(new MaterialLotDto(1L)), dto.getLots());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(dto.getId());
        dto.setId(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testAccessShipment() {
        assertNull(dto.getShipmentNumber());
        dto.setShipmentNumber("SHIPMENT");
        assertEquals("SHIPMENT", dto.getShipmentNumber());
    }

    @Test
    public void testAccessDescription() {
        assertNull(dto.getDescription());
        dto.setDescription("DESCRIPTION");
        assertEquals("DESCRIPTION", dto.getDescription());
    }

    @Test
    public void testAccessStatus() {
        assertNull(dto.getShipmentStatus());
        dto.setShipmentStatus(new ShipmentStatusDto(99L));
        assertEquals(new ShipmentStatusDto(99L), dto.getShipmentStatus());
    }

    @Test
    public void testAccessDeliveryDueDate() {
        assertNull(dto.getDeliveryDueDate());
        dto.setDeliveryDueDate(LocalDateTime.of(1999, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), dto.getDeliveryDueDate());
    }

    @Test
    public void testAccessLots() {
        assertNull(dto.getLots());
        dto.setLots(List.of(new MaterialLotDto(1L)));
        assertEquals(List.of(new MaterialLotDto(1L)), dto.getLots());
    }

    @Test
    public void testAccessDeliveredDate() {
        assertNull(dto.getDeliveredDate());
        dto.setDeliveredDate(LocalDateTime.of(2000, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), dto.getDeliveredDate());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(dto.getCreatedAt());
        dto.setCreatedAt(LocalDateTime.of(1999, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), dto.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(dto.getLastUpdated());
        dto.setLastUpdated(LocalDateTime.of(1999, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), dto.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}
