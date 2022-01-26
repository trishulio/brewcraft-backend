package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddFinishedGoodLotPortionDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.FinishedGoodLotPortionDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLotPortionDto;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodLotFinishedGoodLotPortionMapperTest {

    private FinishedGoodLotFinishedGoodLotPortionMapper finishedGoodMapper;

    @BeforeEach
    public void init() {
        finishedGoodMapper = FinishedGoodLotFinishedGoodLotPortionMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddFinishedGoodLotPortionDto dto = new AddFinishedGoodLotPortionDto(
            5L,
            new QuantityDto("each", BigDecimal.valueOf(100))
        );

        FinishedGoodLotFinishedGoodLotPortion finishedGood = finishedGoodMapper.fromDto(dto);

        FinishedGoodLotFinishedGoodLotPortion expectedFinishedGood = new FinishedGoodLotFinishedGoodLotPortion(
            null,
            new FinishedGoodLot(5L),
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
            null,
            null,
            null,
            null
            );

        assertEquals(expectedFinishedGood, finishedGood);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateFinishedGoodLotPortionDto dto = new UpdateFinishedGoodLotPortionDto(
            1L,
            5L,
            new QuantityDto("each", BigDecimal.valueOf(100)),
            1
        );

        FinishedGoodLotFinishedGoodLotPortion finishedGood = finishedGoodMapper.fromDto(dto);

        FinishedGoodLotFinishedGoodLotPortion expectedFinishedGood = new FinishedGoodLotFinishedGoodLotPortion(
            1L,
            new FinishedGoodLot(5L),
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
            null,
            null,
            null,
            1
        );

        assertEquals(expectedFinishedGood, finishedGood);
    }

    @Test
    public void testToDto_ReturnsDto() {
        FinishedGoodLotFinishedGoodLotPortion finishedGood = new FinishedGoodLotFinishedGoodLotPortion(
            1L,
            new FinishedGoodLot(5L),
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
            new FinishedGoodLot(10L),
            LocalDateTime.of(2019, 1, 2, 3, 4),
            LocalDateTime.of(2020, 1, 2, 3, 4),
            1
        );

        FinishedGoodLotPortionDto dto = finishedGoodMapper.toDto(finishedGood);

        FinishedGoodLotPortionDto expectedDto = new FinishedGoodLotPortionDto(
            1L,
            new FinishedGoodLotDto(5L),
            new QuantityDto("each", BigDecimal.valueOf(100)),
            1
        );

        assertEquals(expectedDto, dto);
    }
}
