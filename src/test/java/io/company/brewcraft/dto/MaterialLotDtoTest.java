package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialLotDtoTest {
    private MaterialLotDto dto;

    @BeforeEach
    public void init() {
        dto = new MaterialLotDto();
    }

    @Test
    public void testIdArgConstructor() {
        dto = new MaterialLotDto(100L);
        assertEquals(100L, dto.getId());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new MaterialLotDto(1L, "LOT_1", new QuantityDto("g", new BigDecimal("10.00")), new InvoiceItemDto(200L), new StorageDto(300L), LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        assertEquals(1L, dto.getId());
        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals(new QuantityDto("g", new BigDecimal("10.00")), dto.getQuantity());
        assertEquals(new InvoiceItemDto(200L), dto.getInvoiceItem());
        assertEquals(new StorageDto(300L), dto.getStorage());
        assertEquals(LocalDateTime.of(1999, 1, 1, 0, 0), dto.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getLastUpdated());
        assertEquals(1, dto.getVersion());
    }

    @Test
    public void testAccessId() {
        assertNull(dto.getId());
        dto.setId(1L);
        assertEquals(1L, dto.getId());
    }

    @Test
    public void testAccessLotNumber() {
        assertNull(dto.getLotNumber());
        dto.setLotNumber("LOT");
        assertEquals("LOT", dto.getLotNumber());
    }

    @Test
    public void testAccessQuantity() {
        assertNull(dto.getQuantity());
        dto.setQuantity(new QuantityDto("g", new BigDecimal("10.00")));
        assertEquals(new QuantityDto("g", new BigDecimal("10.00")), dto.getQuantity());
    }

    @Test
    public void testAccessInvoiceItemDto() {
        assertNull(dto.getInvoiceItem());
        dto.setInvoiceItem(new InvoiceItemDto(1L));
        assertEquals(new InvoiceItemDto(1L), dto.getInvoiceItem());
    }

    @Test
    public void testAccessStorage() {
        assertNull(dto.getStorage());
        dto.setStorage(new StorageDto(1L));
        assertEquals(new StorageDto(1L), dto.getStorage());
    }

    @Test
    public void testAccessCreatedAt() {
        assertNull(dto.getCreatedAt());
        dto.setCreatedAt(LocalDateTime.of(1000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(1000, 1, 1, 0, 0), dto.getCreatedAt());
    }

    @Test
    public void testAccessLastUpdated() {
        assertNull(dto.getLastUpdated());
        dto.setLastUpdated(LocalDateTime.of(2000, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2000, 1, 1, 0, 0), dto.getLastUpdated());
    }

    @Test
    public void testAccessVersion() {
        assertNull(dto.getVersion());
        dto.setVersion(99);
        assertEquals(99, dto.getVersion());
    }
}
