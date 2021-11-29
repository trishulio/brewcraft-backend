package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.UpdateProcurementShipmentDto;

public class UpdateProcurementShipmentDtoTest {
    private UpdateProcurementShipmentDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateProcurementShipmentDto();
    }

    @Test
    public void testAllArgConstructor_SetsAllFieldValues() {
        dto = new UpdateProcurementShipmentDto(1L, "SHIPMENT_1", "DESCRIPTION_1", 99L, LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        assertEquals(1L, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(99L, dto.getShipmentStatusId());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getDeliveredDate());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        UpdateProcurementShipmentDto dto = new UpdateProcurementShipmentDto(1L);

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
    public void testAccessStatusId() {
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
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}
