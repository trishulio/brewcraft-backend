package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementItemDto;

public class UpdateProcurementItemDtoTest {
    private UpdateProcurementItemDto dto;

    @BeforeEach
    public void init() {
        dto = new UpdateProcurementItemDto();
    }

    @Test
    public void testNoArgConstructor_SetsAllValuesAsNull() {
        assertNull(dto.getId());
        assertNull(dto.getDescription());
        assertNull(dto.getLotNumber());
        assertNull(dto.getQuantity());
        assertNull(dto.getPrice());
        assertNull(dto.getTax());
        assertNull(dto.getMaterialId());
        assertNull(dto.getStorageId());
        assertNull(dto.getInvoiceItemVersion());
        assertNull(dto.getVersion());
    }

    @Test
    public void testIdArgConstructor_SetsIdValue() {
        dto = new UpdateProcurementItemDto(new ProcurementItemIdDto(10L, 20L));

        assertEquals(new ProcurementItemIdDto(10L, 20L), dto.getId());
    }

    @Test
    public void testAllArgConstructor_SetsAllValues() {
        dto = new UpdateProcurementItemDto(
                new ProcurementItemIdDto(10L, 20L),
                "DESCRIPTION", // description
                "LOT_NUMBER", // lotNumber
                new QuantityDto("kg", new BigDecimal("10")), // quantity
                new MoneyDto("CAD", new BigDecimal("20")), // price
                new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
                1L, // materialId
                2L, // storageId,
                2, // invoiceItemVersion
                1 // version
        );

        assertEquals(new ProcurementItemIdDto(10L, 20L), dto.getId());
        assertEquals("DESCRIPTION", dto.getDescription());
        assertEquals("LOT_NUMBER", dto.getLotNumber());
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), dto.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("20")), dto.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), dto.getTax());
        assertEquals(1L, dto.getMaterialId());
        assertEquals(2L, dto.getStorageId());
        assertEquals(2, dto.getInvoiceItemVersion());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        dto.setId(new ProcurementItemIdDto(100L, 200L));
        assertEquals(new ProcurementItemIdDto(100L, 200L), dto.getId());
    }

    @Test
    public void testAccessId_ReturnsNull_WhenShipmentOrInvoiceIsNull() {
        dto.setId(new ProcurementItemIdDto(null, null));
        assertNull(dto.getId());

        dto.setId(null);
        assertNull(dto.getId());
    }

    @Test
    public void testAccessDescription() {
        dto.setDescription("DESCRIPTION_1");
        assertEquals("DESCRIPTION_1", dto.getDescription());
    }

    @Test
    public void testAccessLotNumber() {
        dto.setLotNumber("LOT_NUMBER_1");
        assertEquals("LOT_NUMBER_1", dto.getLotNumber());
    }

    @Test
    public void testAccessQuantity() {
        dto.setQuantity(new QuantityDto("kg", new BigDecimal("10")));
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), dto.getQuantity());
    }

    @Test
    public void testAccessPrice() {
        dto.setPrice(new MoneyDto("CAD", new BigDecimal("20")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("20")), dto.getPrice());
    }

    @Test
    public void testAccessTax() {
        dto.setTax(new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))));
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), dto.getTax());
    }

    @Test
    public void testAccessMaterialId() {
        dto.setMaterialId(1L);
        assertEquals(1L, dto.getMaterialId());
    }

    @Test
    public void testAccessStorageId() {
        dto.setStorageId(2L);
        assertEquals(2L, dto.getStorageId());
    }

    @Test
    public void testAccessInvoiceItemVersion() {
        dto.setInvoiceItemVersion(1);
        assertEquals(1, dto.getInvoiceItemVersion());
    }

    @Test
    public void testAccessVersion() {
        dto.setVersion(1);
        assertEquals(1, dto.getVersion());
    }
}
