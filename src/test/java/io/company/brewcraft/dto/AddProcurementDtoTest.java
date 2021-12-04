package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.AddProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.AddProcurementShipmentDto;

public class AddProcurementDtoTest {
    private AddProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new AddProcurementDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getInvoice());
        assertNull(dto.getShipment());
        assertNull(dto.getProcurementItems());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new AddProcurementDto(
            new AddProcurementInvoiceDto(),
            new AddProcurementShipmentDto(),
            List.of(new AddProcurementItemDto())
        );

        assertEquals(new AddProcurementInvoiceDto(), dto.getInvoice());
        assertEquals(new AddProcurementShipmentDto(), dto.getShipment());
        assertEquals(List.of(new AddProcurementItemDto()), dto.getProcurementItems());
    }

    @Test
    public void testAccessInvoice() {
        dto.setInvoice(new AddProcurementInvoiceDto());

        assertEquals(new AddProcurementInvoiceDto(), dto.getInvoice());
    }

    @Test
    public void testAccessShipment() {
        dto.setShipment(new AddProcurementShipmentDto());

        assertEquals(new AddProcurementShipmentDto(), dto.getShipment());
    }

    @Test
    public void testAccessAddProcurementItems() {
        dto.setProcurementItems(List.of(new AddProcurementItemDto()));

        assertEquals(List.of(new AddProcurementItemDto()), dto.getProcurementItems());
    }
}
