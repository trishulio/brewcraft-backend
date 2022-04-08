package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddFinishedGoodLotPortionDtoTest {
    private AddFinishedGoodLotPortionDto addFinishedGoodLotPortionDto;

    @BeforeEach
    public void init() {
        addFinishedGoodLotPortionDto = new AddFinishedGoodLotPortionDto();
    }

    @Test
    public void testConstructor() {
        Long finishedGoodLotId = 1L;
        QuantityDto quantityDto = new QuantityDto("each", new BigDecimal("100"));

        AddFinishedGoodLotPortionDto addFinishedGoodLotPortionDto = new AddFinishedGoodLotPortionDto(finishedGoodLotId, quantityDto);

        assertEquals(1L, addFinishedGoodLotPortionDto.getFinishedGoodLotId());
        assertEquals(new QuantityDto("each", new BigDecimal("100")), addFinishedGoodLotPortionDto.getQuantity());
    }

    @Test
    public void testGetSetFinishedGoodLotId() {
        addFinishedGoodLotPortionDto.setFinishedGoodLotId(1L);
        assertEquals(1L, addFinishedGoodLotPortionDto.getFinishedGoodLotId());
    }

    @Test
    public void testGetSetQuantity() {
        addFinishedGoodLotPortionDto.setQuantity(new QuantityDto("each", new BigDecimal("100")));
        assertEquals(new QuantityDto("each", new BigDecimal("100")), addFinishedGoodLotPortionDto.getQuantity());
    }
}
