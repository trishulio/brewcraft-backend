package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StockLotDtoTest {

    private StockLotDto dto;

    @BeforeEach
    public void init() {
        this.dto = new StockLotDto();
    }

    @Test
    public void testAllArgConstructor() {
        this.dto = new StockLotDto(99L, "LOT_99", new QuantityDto("kg", new BigDecimal("10")), new InvoiceItemDto(99L), new StorageDto(99L), new MaterialDto(98L));

        assertEquals(99L, this.dto.getId());
        assertEquals("LOT_99", this.dto.getLotNumber());
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), this.dto.getQuantity());
        assertEquals(new InvoiceItemDto(99L), this.dto.getInvoiceItem());
        assertEquals(new StorageDto(99L), this.dto.getStorage());
        assertEquals(new MaterialDto(98L), this.dto.getMaterial());
    }

    @Test
    public void testAccessId() {
        assertNull(this.dto.getId());
        this.dto.setId(99L);
        assertEquals(99L, this.dto.getId());
    }

    @Test
    public void testAccessLotNumber() {
        assertNull(this.dto.getLotNumber());
        this.dto.setLotNumber("LOT_99");
        assertEquals("LOT_99", this.dto.getLotNumber());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(this.dto.getQuantity());
        this.dto.setQuantity(new QuantityDto("kg", new BigDecimal("10")));
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), this.dto.getQuantity());
    }

    @Test
    public void testAccessInvoiceItem() {
        assertNull(this.dto.getInvoiceItem());
        this.dto.setInvoiceItem(new InvoiceItemDto(99L));
        assertEquals(new InvoiceItemDto(99L), this.dto.getInvoiceItem());
    }

    @Test
    public void testAccessStorage() {
        assertNull(this.dto.getStorage());
        this.dto.setStorage(new StorageDto(99L));
        assertEquals(new StorageDto(99L), this.dto.getStorage());
    }

    @Test
    public void testAccessMaterial() {
        assertNull(this.dto.getMaterial());
        this.dto.setMaterial(new MaterialDto(98L));
        assertEquals(new MaterialDto(98L), this.dto.getMaterial());
    }
}
