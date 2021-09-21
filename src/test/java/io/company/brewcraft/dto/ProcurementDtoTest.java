package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementDto;

public class ProcurementDtoTest {

    private ProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new ProcurementDto();
    }

    @Test
    public void testAllArgConstructor() {
        dto = new ProcurementDto(new PurchaseOrderDto(1L), new InvoiceDto(2L));

        assertEquals(new PurchaseOrderDto(1L), dto.getPurchaseOrder());
        assertEquals(new InvoiceDto(2L), dto.getInvoice());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(dto.getPurchaseOrder());

        dto.setPurchaseOrder(new PurchaseOrderDto(1L));
        assertEquals(new PurchaseOrderDto(1L), dto.getPurchaseOrder());
    }

    @Test
    public void testAccessInvoice() {
        assertNull(dto.getInvoice());

        dto.setInvoice(new InvoiceDto(1L));
        assertEquals(new InvoiceDto(1L), dto.getInvoice());
    }
}
