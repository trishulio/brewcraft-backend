package io.company.brewcraft.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementItemDto;
import io.company.brewcraft.dto.procurement.ProcurementItemIdDto;

public class ProcurementItemDtoTest {
    private ProcurementItemDto dto;

    @BeforeEach
    public void init() {
        dto = new ProcurementItemDto();
    }

    @Test
    public void testNoArgConstructor_SetsAllValuesAsNull() {
        assertNull(dto.getId());
        assertNull(dto.getDescription());
        assertNull(dto.getLotNumber());
        assertNull(dto.getQuantity());
        assertNull(dto.getPrice());
        assertNull(dto.getTax());
        assertNull(dto.getMaterial());
        assertNull(dto.getStorage());
        assertNull(dto.getInvoiceItemVersion());
        assertNull(dto.getVersion());
    }

    @Test
    public void testIdArgConstructor_SetsIdValue() {
        dto = new ProcurementItemDto(new ProcurementItemIdDto(10L, 100L));

        assertEquals(new ProcurementItemIdDto(10L, 100L), dto.getId());
    }

    @Test
    public void testAllArgConstructor_SetsAllValues() {
        dto = new ProcurementItemDto(
            new ProcurementItemIdDto(10L, 20L),
            "DESCRIPTION", // description
            "LOT_NUMBER", // lotNumber
            new QuantityDto("kg", new BigDecimal("10")), // quantity
            new MoneyDto("CAD", new BigDecimal("20")), // price
            new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), // tax
            new MoneyDto("CAD", new BigDecimal("50")), // amount
            new MaterialDto(1L), // materialId
            new StorageDto(2L), // storageId,
            LocalDateTime.of(2000, 12, 1, 0, 0), // createdAt
            LocalDateTime.of(2001, 12, 1, 0, 0), // lastUpdated
            2, // invoiceItemVersion
            1 // version
        );

        assertEquals(new ProcurementItemIdDto(10L, 20L), dto.getId());
        assertEquals("DESCRIPTION", dto.getDescription());
        assertEquals("LOT_NUMBER", dto.getLotNumber());
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), dto.getQuantity());
        assertEquals(new MoneyDto("CAD", new BigDecimal("20")), dto.getPrice());
        assertEquals(new TaxDto(new MoneyDto("CAD", new BigDecimal("5"))), dto.getTax());
        assertEquals(new MoneyDto("CAD", new BigDecimal("50")), dto.getAmount());
        assertEquals(new MaterialDto(1L), dto.getMaterial());
        assertEquals(new StorageDto(2L), dto.getStorage());
        assertEquals(LocalDateTime.of(2000, 12, 1, 0, 0), dto.getCreatedAt());
        assertEquals(LocalDateTime.of(2001, 12, 1, 0, 0), dto.getLastUpdated());
        assertEquals(2, dto.getInvoiceItemVersion());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        dto.setId(new ProcurementItemIdDto(100L, 200L));
        assertEquals(new ProcurementItemIdDto(100L, 200L), dto.getId());
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
    public void testAccessAmount() {
        dto.setAmount(new MoneyDto("CAD", new BigDecimal("20")));
        assertEquals(new MoneyDto("CAD", new BigDecimal("20")), dto.getAmount());
    }

    @Test
    public void testAccessMaterial() {
        dto.setMaterial(new MaterialDto(1L));
        assertEquals(new MaterialDto(1L), dto.getMaterial());
    }

    @Test
    public void testAccessStorage() {
        dto.setStorage(new StorageDto(2L));
        assertEquals(new StorageDto(2L), dto.getStorage());
    }

    @Test
    public void testAccessCreatedAt() {
        dto.setCreatedAt(LocalDateTime.of(1999, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        dto.setLastUpdated(LocalDateTime.of(1999, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getLastUpdated());
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
