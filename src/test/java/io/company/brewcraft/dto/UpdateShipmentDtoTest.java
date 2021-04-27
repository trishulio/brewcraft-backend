package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateShipmentDtoTest {
    private UpdateShipmentDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateShipmentDto();
    }

    @Test
    public void testAllArgConstructor_SetsAllFieldValues() {
        List<UpdateMaterialLotDto> items = List.of(new UpdateMaterialLotDto(100L));
        dto = new UpdateShipmentDto("SHIPMENT_1", "DESCRIPTION_1", "STATUS", LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), items, 1);

        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals("STATUS", dto.getStatus());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getDeliveredDate());
        assertEquals(List.of(new UpdateMaterialLotDto(100L)), dto.getLots());
        assertEquals(1, dto.getVersion());
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
        assertNull(dto.getStatus());
        dto.setStatus("RECEIVED");
        assertEquals("RECEIVED", dto.getStatus());
    }

    @Test
    public void testAccessDeliveryDueDate() {
        assertNull(dto.getDeliveryDueDate());
        dto.setDeliveryDueDate(LocalDateTime.of(1999, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(1999, 12, 12, 0, 0), dto.getDeliveryDueDate());
    }

    @Test
    public void testAccessDeliveredDate() {
        assertNull(dto.getDeliveredDate());
        dto.setDeliveredDate(LocalDateTime.of(2000, 12, 12, 0, 0));
        assertEquals(LocalDateTime.of(2000, 12, 12, 0, 0), dto.getDeliveredDate());
    }

    @Test
    public void testAccessLots() {
        assertNull(dto.getLots());
        dto.setLots(List.of(new UpdateMaterialLotDto()));
        assertEquals(List.of(new UpdateMaterialLotDto()), dto.getLots());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}
