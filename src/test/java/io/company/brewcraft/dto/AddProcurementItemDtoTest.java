package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.AddProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.AddProcurementItemDto;
import io.company.brewcraft.dto.procurement.AddProcurementMaterialLotDto;

public class AddProcurementItemDtoTest {
    private AddProcurementItemDto dto;

    @BeforeEach
    public void init() {
        dto = new AddProcurementItemDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getInvoiceItem());
        assertNull(dto.getMaterialLot());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new AddProcurementItemDto(new AddProcurementMaterialLotDto(), new AddProcurementInvoiceItemDto());

        assertEquals(new AddProcurementMaterialLotDto(), dto.getMaterialLot());
        assertEquals(new AddProcurementInvoiceItemDto(), dto.getInvoiceItem());
    }

    @Test
    public void testAccessInvoiceItem() {
        dto.setInvoiceItem(new AddProcurementInvoiceItemDto());
        assertEquals(new AddProcurementInvoiceItemDto(), dto.getInvoiceItem());
    }

    @Test
    public void testAccessMaterialLot() {
        dto.setMaterialLot(new AddProcurementMaterialLotDto());
        assertEquals(new AddProcurementMaterialLotDto(), dto.getMaterialLot());
    }
}
