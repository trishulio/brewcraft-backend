package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.FinishedGoodInventoryAggregationDto;
import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.FinishedGoodLotPortionDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.MixturePortionDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.FinishedGoodInventoryAggregation;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodInventoryMapperTest {
    private FinishedGoodInventoryMapper finishedGoodInventoryMapper;

    @BeforeEach
    public void init() {
        finishedGoodInventoryMapper = FinishedGoodInventoryMapper.INSTANCE;
    }

    @Test
    public void testToFinishedGoodInventoryAggregationDtoDto_ReturnsDto() {
        FinishedGoodInventoryAggregation finishedGoodInventoryAggregation = new FinishedGoodInventoryAggregation(1L, new Sku(2L), LocalDateTime.of(1995, 1, 1, 1, 1), Quantities.getQuantity(BigDecimal.valueOf(50), SupportedUnits.EACH));
        FinishedGoodInventoryAggregationDto dto = finishedGoodInventoryMapper.toDto(finishedGoodInventoryAggregation);

        assertEquals(new FinishedGoodInventoryAggregationDto(1L, new SkuDto(2L), new QuantityDto("each", BigDecimal.valueOf(50)), LocalDateTime.of(1995, 1, 1, 1, 1)), dto);
    }

    @Test
    public void testToFinishedGoodInventoryDto_ReturnsDto() {
        FinishedGoodInventory finishedGoodInventory = new FinishedGoodInventory(
                10L,
                new Sku(5L),
                List.of(new FinishedGoodLotMixturePortion(6L, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.GRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
                List.of(new FinishedGoodLotMaterialPortion(7L, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.GRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
                List.of(new FinishedGoodLotFinishedGoodLotPortion(8L, new FinishedGoodLot(9L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(10L), null, null, 1)),
                Quantities.getQuantity(100.0, SupportedUnits.EACH),
                LocalDateTime.of(1995, 1, 1, 1, 1)
        );

        FinishedGoodInventoryDto dto = finishedGoodInventoryMapper.toDto(finishedGoodInventory);

        FinishedGoodInventoryDto expected = new FinishedGoodInventoryDto(
                10L,
                new SkuDto(5L),
                List.of(new MixturePortionDto(6L, new MixtureDto(8L), new QuantityDto("g", BigDecimal.valueOf(4)), 1)),
                List.of(new MaterialPortionDto(7L, new MaterialLotDto(8L), new QuantityDto("g", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
                List.of(new FinishedGoodLotPortionDto(8L, new FinishedGoodLotDto(9L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)),
                LocalDateTime.of(1995, 1, 1, 1, 1),
                new QuantityDto("each", BigDecimal.valueOf(100.0))
        );

        assertEquals(expected, dto);
    }
}
