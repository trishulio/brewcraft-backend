package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinishedGoodLotPortionDtoTest {
    private FinishedGoodLotPortionDto finishedGoodLotPortionDto;

    @BeforeEach
    public void init() {
        finishedGoodLotPortionDto = new FinishedGoodLotPortionDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        FinishedGoodLotDto finishedGoodLotDto = new FinishedGoodLotDto(3L);
        QuantityDto quantityDto = new QuantityDto("each", new BigDecimal("100"));
        Integer version = 1;

        FinishedGoodLotPortionDto finishedGoodLotPortionDto = new FinishedGoodLotPortionDto(id, finishedGoodLotDto, quantityDto, version);

        assertEquals(1L, finishedGoodLotPortionDto.getId());
        assertEquals(new FinishedGoodLotDto(3L), finishedGoodLotPortionDto.getFinishedGoodLot());
        assertEquals(new QuantityDto("each", new BigDecimal("100")), finishedGoodLotPortionDto.getQuantity());
        assertEquals(1, finishedGoodLotPortionDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        finishedGoodLotPortionDto.setId(1L);
        assertEquals(1L, finishedGoodLotPortionDto.getId());
    }

    @Test
    public void testGetSetFinishedGoodLot() {
        finishedGoodLotPortionDto.setFinishedGoodLot(new FinishedGoodLotDto(3L));

        assertEquals(new FinishedGoodLotDto(3L), finishedGoodLotPortionDto.getFinishedGoodLot());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodLotPortionDto.setQuantity(new QuantityDto("each", new BigDecimal("100")));
        assertEquals(new QuantityDto("each", new BigDecimal("100")), finishedGoodLotPortionDto.getQuantity());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGoodLotPortionDto.setVersion(version);
        assertEquals(version, finishedGoodLotPortionDto.getVersion());
    }
}
