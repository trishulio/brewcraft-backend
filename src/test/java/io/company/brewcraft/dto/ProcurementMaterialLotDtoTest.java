package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.procurement.ProcurementMaterialLotDto;

public class ProcurementMaterialLotDtoTest {
    private ProcurementMaterialLotDto dto;

    @BeforeEach
    public void init() {
        dto = new ProcurementMaterialLotDto();
    }

    @Test
    public void testIdArgConstructor() {
        dto = new ProcurementMaterialLotDto(100L);
        assertEquals(100L, dto.getId());
    }

    @Test
    public void testAllArgConstructor() {
        dto = new ProcurementMaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10.00")), new StorageDto(300L), LocalDateTime.of(1999, 1, 1, 0, 0), LocalDateTime.of(2000, 1, 1, 0, 0), 1);

        assertEquals(1L, dto.getId());
        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), dto.getQuantity());
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
        dto.setQuantity(new QuantityDto("kg", new BigDecimal("10.00")));
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), dto.getQuantity());
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
