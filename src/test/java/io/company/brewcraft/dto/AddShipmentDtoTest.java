package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddShipmentDtoTest {
    private AddShipmentDto dto;

    @BeforeEach
    public void init() {
        dto = new AddShipmentDto();
    }

    @Test
    public void testAllArgConstructor_SetsAllFieldValues() {
        List<AddMaterialLotDto> items = List.of(new AddMaterialLotDto());
        dto = new AddShipmentDto("SHIPMENT_1", "DESCRIPTION_1", 99L, LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), items);

        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(99L, dto.getShipmentStatusId());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getDeliveredDate());
        assertEquals(List.of(new AddMaterialLotDto()), dto.getLots());
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
        assertNull(dto.getShipmentStatusId());
        dto.setShipmentStatusId(99L);
        assertEquals(99L, dto.getShipmentStatusId());
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
        dto.setLots(List.of(new AddMaterialLotDto()));
        assertEquals(List.of(new AddMaterialLotDto()), dto.getLots());
    }
}
