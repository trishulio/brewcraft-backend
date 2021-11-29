package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementPurchaseOrderDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementShipmentDto;

public class UpdateProcurementDtoTest {
    private UpdateProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateProcurementDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getInvoice());
        assertNull(dto.getShipment());
        assertNull(dto.getPurchaseOrder());
        assertNull(dto.getProcurementItems());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new UpdateProcurementDto(
            new UpdateProcurementInvoiceDto(),
            new UpdateProcurementShipmentDto(),
            new UpdateProcurementPurchaseOrderDto(),
            List.of(new UpdateProcurementItemDto())
        );

        assertEquals(new UpdateProcurementInvoiceDto(), dto.getInvoice());
        assertEquals(new UpdateProcurementShipmentDto(), dto.getShipment());
        assertEquals(new UpdateProcurementPurchaseOrderDto(), dto.getPurchaseOrder());
        assertEquals(List.of(new UpdateProcurementItemDto()), dto.getProcurementItems());
    }

    @Test
    public void testAccessInvoice() {
        dto.setInvoice(new UpdateProcurementInvoiceDto());

        assertEquals(new UpdateProcurementInvoiceDto(), dto.getInvoice());
    }

    @Test
    public void testAccessShipment() {
        dto.setShipment(new UpdateProcurementShipmentDto());

        assertEquals(new UpdateProcurementShipmentDto(), dto.getShipment());
    }

    @Test
    public void testAccessPurchaseOrder() {
        dto.setPurchaseOrder(new UpdateProcurementPurchaseOrderDto());

        assertEquals(new UpdateProcurementPurchaseOrderDto(), dto.getPurchaseOrder());
    }

    @Test
    public void testAccessUpdateProcurementItems() {
        dto.setProcurementItems(List.of(new UpdateProcurementItemDto()));

        assertEquals(List.of(new UpdateProcurementItemDto()), dto.getProcurementItems());
    }
}
