package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;

public class ProcurementItemIdDtoTest {
    private ProcurementItemIdDto id;

    @BeforeEach
    public void init() {
        id = new ProcurementItemIdDto();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(id.getLotId());
        assertNull(id.getInvoiceItemId());
    }

    @Test
    public void testAllArgConstructor() {
        id = new ProcurementItemIdDto(1L, 10L);

        assertEquals(1L, id.getLotId());
        assertEquals(10L, id.getInvoiceItemId());
    }

    @Test
    public void testAccessLotId() {
        id.setLotId(1L);
        assertEquals(1L, id.getLotId());
    }

    @Test
    public void testAccessInvoiceItemId() {
        id.setInvoiceItemId(10L);
        assertEquals(10L, id.getInvoiceItemId());
    }
}
