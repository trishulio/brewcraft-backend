package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementInvoiceItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.ProcurementMaterialLotDto;

public class ProcurementItemDtoTest {
    private ProcurementItemDto dto;

    @BeforeEach
    public void init() {
        dto = new ProcurementItemDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(dto.getId());
        assertNull(dto.getInvoiceItem());
        assertNull(dto.getMaterialLot());
    }

    @Test
    public void testIdArgConstructor() {
        dto = new ProcurementItemDto(new ProcurementItemIdDto(1L, 10L));

        assertEquals(new ProcurementItemIdDto(1L, 10L), dto.getId());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new ProcurementItemDto(new ProcurementItemIdDto(1L, 10L), new ProcurementMaterialLotDto(1L), new ProcurementInvoiceItemDto(10L));

        assertEquals(new ProcurementItemIdDto(1L, 10L), dto.getId());
        assertEquals(new ProcurementMaterialLotDto(1L), dto.getMaterialLot());
        assertEquals(new ProcurementInvoiceItemDto(10L), dto.getInvoiceItem());
    }

    @Test
    public void testAccessId() {
        dto.setId(new ProcurementItemIdDto(1L, 10L));

        assertEquals(new ProcurementItemIdDto(1L, 10L), dto.getId());
    }

    @Test
    public void testAccessInvoiceItem() {
        dto.setInvoiceItem(new ProcurementInvoiceItemDto(10L));
        assertEquals(new ProcurementInvoiceItemDto(10L), dto.getInvoiceItem());
    }

    @Test
    public void testAccessMaterialLot() {
        dto.setMaterialLot(new ProcurementMaterialLotDto(1L));
        assertEquals(new ProcurementMaterialLotDto(1L), dto.getMaterialLot());
    }
}
