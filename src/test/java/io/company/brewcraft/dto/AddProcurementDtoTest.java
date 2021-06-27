package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.AddProcurementDto;

public class AddProcurementDtoTest {

    private AddProcurementDto dto;

    @BeforeEach
    public void init() {
        dto = new AddProcurementDto();
    }

    @Test
    public void testAllArgConstructor() {
        dto = new AddProcurementDto(new AddPurchaseOrderDto(), new AddInvoiceDto());

        assertEquals(new AddPurchaseOrderDto(), dto.getPurchaseOrder());
        assertEquals(new AddInvoiceDto(), dto.getInvoice());
    }

    @Test
    public void testAccessPurchaseOrder() {
        assertNull(dto.getPurchaseOrder());

        dto.setPurchaseOrder(new AddPurchaseOrderDto());
        assertEquals(new AddPurchaseOrderDto(), dto.getPurchaseOrder());
    }

    @Test
    public void testAccessInvoice() {
        assertNull(dto.getInvoice());

        dto.setInvoice(new AddInvoiceDto());
        assertEquals(new AddInvoiceDto(), dto.getInvoice());
    }
}
