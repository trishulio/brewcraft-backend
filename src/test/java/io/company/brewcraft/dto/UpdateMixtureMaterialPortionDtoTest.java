package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMixtureMaterialPortionDtoTest {

    private UpdateMixtureMaterialPortionDto updateMixtureMaterialPortionDto;

    @BeforeEach
    public void init() {
        updateMixtureMaterialPortionDto = new UpdateMixtureMaterialPortionDto();
    }

    @Test
    public void testConstructor() {
        Long materialLotId = 2L;
        QuantityDto quantityDto = new QuantityDto("kg", new BigDecimal("100"));
        Long mixtureId = 1L;
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        UpdateMixtureMaterialPortionDto updateMixtureMaterialPortionDto = new UpdateMixtureMaterialPortionDto(materialLotId, quantityDto, mixtureId, addedAt, version);

        assertEquals(2L, updateMixtureMaterialPortionDto.getMaterialLotId());
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), updateMixtureMaterialPortionDto.getQuantity());
        assertEquals(1L, updateMixtureMaterialPortionDto.getMixtureId());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), updateMixtureMaterialPortionDto.getAddedAt());
        assertEquals(1, updateMixtureMaterialPortionDto.getVersion());
    }

    @Test
    public void testGetSetMaterialLotId() {
        updateMixtureMaterialPortionDto.setMaterialLotId(3L);

        assertEquals(3L, updateMixtureMaterialPortionDto.getMaterialLotId());
    }

    @Test
    public void testGetSetQuantity() {
        updateMixtureMaterialPortionDto.setQuantity(new QuantityDto("kg", new BigDecimal("100")));
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), updateMixtureMaterialPortionDto.getQuantity());
    }

    @Test
    public void testGetSetAddedAt() {
        updateMixtureMaterialPortionDto.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), updateMixtureMaterialPortionDto.getAddedAt());
    }

    @Test
    public void testGetSetMixtureId() {
        updateMixtureMaterialPortionDto.setMixtureId(2L);
        assertEquals(2L, updateMixtureMaterialPortionDto.getMixtureId());
    }

    @Test
    public void testGetSetVersion() {
        updateMixtureMaterialPortionDto.setVersion(1);
        assertEquals(1, updateMixtureMaterialPortionDto.getVersion());
    }

}
