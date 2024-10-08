package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMaterialPortionDtoTest {
    private UpdateMaterialPortionDto updateMaterialPortionBaseDto;

    @BeforeEach
    public void init() {
        updateMaterialPortionBaseDto = new UpdateMaterialPortionDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Long materialLotId = 2L;
        QuantityDto quantityDto = new QuantityDto("g", new BigDecimal("100"));
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        UpdateMaterialPortionDto updateMaterialPortionBaseDto = new UpdateMaterialPortionDto(id, materialLotId, quantityDto, addedAt, version);

        assertEquals(1L, updateMaterialPortionBaseDto.getId());
        assertEquals(2L, updateMaterialPortionBaseDto.getMaterialLotId());
        assertEquals(new QuantityDto("g", new BigDecimal("100")), updateMaterialPortionBaseDto.getQuantity());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), updateMaterialPortionBaseDto.getAddedAt());
        assertEquals(1, updateMaterialPortionBaseDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        updateMaterialPortionBaseDto.setId(1L);
        assertEquals(1L, updateMaterialPortionBaseDto.getId());
    }

    @Test
    public void testGetSetMaterialLotId() {
        updateMaterialPortionBaseDto.setMaterialLotId(3L);
        assertEquals(3L, updateMaterialPortionBaseDto.getMaterialLotId());
    }

    @Test
    public void testGetSetQuantity() {
        updateMaterialPortionBaseDto.setQuantity(new QuantityDto("g", new BigDecimal("100")));
        assertEquals(new QuantityDto("g", new BigDecimal("100")), updateMaterialPortionBaseDto.getQuantity());
    }

    @Test
    public void testGetSetAddedAt() {
        updateMaterialPortionBaseDto.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), updateMaterialPortionBaseDto.getAddedAt());
    }

    @Test
    public void testGetSetVersion() {
        updateMaterialPortionBaseDto.setVersion(1);
        assertEquals(1, updateMaterialPortionBaseDto.getVersion());
    }
}
