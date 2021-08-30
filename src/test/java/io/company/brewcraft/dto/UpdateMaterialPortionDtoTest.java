package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMaterialPortionDtoTest {

    private UpdateMaterialPortionDto updateMaterialPortionDto;

    @BeforeEach
    public void init() {
        updateMaterialPortionDto = new UpdateMaterialPortionDto();
    }

    @Test
    public void testConstructor() {
        Long materialLotId = 2L;
        QuantityDto quantityDto = new QuantityDto("kg", new BigDecimal("100"));
        Long mixtureId = 1L;
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        UpdateMaterialPortionDto updateMaterialPortionDto = new UpdateMaterialPortionDto(materialLotId, quantityDto, mixtureId, addedAt, version);

        assertEquals(2L, updateMaterialPortionDto.getMaterialLotId());
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), updateMaterialPortionDto.getQuantity());
        assertEquals(1L, updateMaterialPortionDto.getMixtureId());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), updateMaterialPortionDto.getAddedAt());
        assertEquals(1, updateMaterialPortionDto.getVersion());
    }

    @Test
    public void testGetSetMaterialLotId() {
        updateMaterialPortionDto.setMaterialLotId(3L);

        assertEquals(3L, updateMaterialPortionDto.getMaterialLotId());
    }

    @Test
    public void testGetSetQuantity() {
        updateMaterialPortionDto.setQuantity(new QuantityDto("kg", new BigDecimal("100")));
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), updateMaterialPortionDto.getQuantity());
    }

    @Test
    public void testGetSetAddedAt() {
        updateMaterialPortionDto.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), updateMaterialPortionDto.getAddedAt());
    }
    
    @Test
    public void testGetSetMixtureId() {
        updateMaterialPortionDto.setMixtureId(2L);
        assertEquals(2L, updateMaterialPortionDto.getMixtureId());
    }
    
    @Test
    public void testGetSetVersion() {
        updateMaterialPortionDto.setVersion(1);
        assertEquals(1, updateMaterialPortionDto.getVersion());
    }

}
