package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.ProcurementInvoiceDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.ProcurementPurchaseOrderDto;
import io.company.brewcraft.dto.procurement.ProcurementShipmentDto;

public class ProcurementDtoTest {
    private ProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new ProcurementDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getId());
        assertNull(dto.getInvoice());
        assertNull(dto.getShipment());
        assertNull(dto.getPurchaseOrder());
    }

    @Test
    public void testIdArgConstructor() {
        dto = new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L));

        assertEquals(new ProcurementIdDto(1L, 10L, 100L), dto.getId());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new ProcurementDto(
            new ProcurementIdDto(1L, 10L, 100L),
            new ProcurementInvoiceDto(1L),
            new ProcurementShipmentDto(10L),
            new ProcurementPurchaseOrderDto(100L),
            List.of(new ProcurementItemDto(new ProcurementItemIdDto(2L, 20L)))
        );

        assertEquals(new ProcurementIdDto(1L, 10L, 100L), dto.getId());
        assertEquals(new ProcurementInvoiceDto(1L), dto.getInvoice());
        assertEquals(new ProcurementShipmentDto(10L), dto.getShipment());
        assertEquals(new ProcurementPurchaseOrderDto(100L), dto.getPurchaseOrder());
        assertEquals(List.of(new ProcurementItemDto(new ProcurementItemIdDto(2L, 20L))), dto.getProcurementItems());
    }

    @Test
    public void testAccessId() {
        dto.setId(new ProcurementIdDto(1L, 10L, 100L));

        assertEquals(new ProcurementIdDto(1L, 10L, 100L), dto.getId());
    }

    @Test
    public void testAccessInvoice() {
        dto.setInvoice(new ProcurementInvoiceDto());

        assertEquals(new ProcurementInvoiceDto(), dto.getInvoice());
    }

    @Test
    public void testAccessShipment() {
        dto.setShipment(new ProcurementShipmentDto());

        assertEquals(new ProcurementShipmentDto(), dto.getShipment());
    }

    @Test
    public void testAccessPurchaseOrder() {
        dto.setPurchaseOrder(new ProcurementPurchaseOrderDto());

        assertEquals(new ProcurementPurchaseOrderDto(), dto.getPurchaseOrder());
    }

    @Test
    public void testAccessProcurementItems() {
        dto.setProcurementItems(List.of(new ProcurementItemDto(new ProcurementItemIdDto(2L, 20L))));

        assertEquals(List.of(new ProcurementItemDto(new ProcurementItemIdDto(2L, 20L))), dto.getProcurementItems());
    }
}
