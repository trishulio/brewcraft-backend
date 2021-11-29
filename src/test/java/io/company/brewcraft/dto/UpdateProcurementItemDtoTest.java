package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.UpdateProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementMaterialLotDto;

public class UpdateProcurementItemDtoTest {
    private UpdateProcurementItemDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateProcurementItemDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getInvoiceItem());
        assertNull(dto.getMaterialLot());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new UpdateProcurementItemDto(new UpdateProcurementMaterialLotDto(), new UpdateProcurementInvoiceItemDto());

        assertEquals(new UpdateProcurementMaterialLotDto(), dto.getMaterialLot());
        assertEquals(new UpdateProcurementInvoiceItemDto(), dto.getInvoiceItem());
    }

    @Test
    public void testAccessInvoiceItem() {
        dto.setInvoiceItem(new UpdateProcurementInvoiceItemDto());
        assertEquals(new UpdateProcurementInvoiceItemDto(), dto.getInvoiceItem());
    }

    @Test
    public void testAccessMaterialLot() {
        dto.setMaterialLot(new UpdateProcurementMaterialLotDto());
        assertEquals(new UpdateProcurementMaterialLotDto(), dto.getMaterialLot());
    }
}
