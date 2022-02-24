package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateFinishedGoodLotPortionDtoTest {

    private UpdateFinishedGoodLotPortionDto updateFinishedGoodLotPortionDto;

    @BeforeEach
    public void init() {
        updateFinishedGoodLotPortionDto = new UpdateFinishedGoodLotPortionDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Long finishedGoodLotId = 2L;
        QuantityDto quantityDto = new QuantityDto("each", new BigDecimal("100"));
        Integer version = 1;

        UpdateFinishedGoodLotPortionDto updateFinishedGoodLotPortionDto = new UpdateFinishedGoodLotPortionDto(id, finishedGoodLotId, quantityDto, version);

        assertEquals(1L, updateFinishedGoodLotPortionDto.getId());
        assertEquals(2L, updateFinishedGoodLotPortionDto.getFinishedGoodLotId());
        assertEquals(new QuantityDto("each", new BigDecimal("100")), updateFinishedGoodLotPortionDto.getQuantity());
        assertEquals(1, updateFinishedGoodLotPortionDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        updateFinishedGoodLotPortionDto.setId(1L);
        assertEquals(1L, updateFinishedGoodLotPortionDto.getId());
    }

    @Test
    public void testGetSetFinishedGoodLotId() {
        updateFinishedGoodLotPortionDto.setFinishedGoodLotId(1L);
        assertEquals(1L, updateFinishedGoodLotPortionDto.getFinishedGoodLotId());
    }

    @Test
    public void testGetSetQuantity() {
        updateFinishedGoodLotPortionDto.setQuantity(new QuantityDto("each", new BigDecimal("100")));
        assertEquals(new QuantityDto("each", new BigDecimal("100")), updateFinishedGoodLotPortionDto.getQuantity());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateFinishedGoodLotPortionDto.setVersion(version);
        assertEquals(version, updateFinishedGoodLotPortionDto.getVersion());
    }
}
