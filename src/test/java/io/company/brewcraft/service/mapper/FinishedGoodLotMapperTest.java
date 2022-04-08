package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddFinishedGoodLotDto;
import io.company.brewcraft.dto.AddFinishedGoodLotPortionDto;
import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.AddMixturePortionDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.FinishedGoodLotPortionDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.MixturePortionDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLotDto;
import io.company.brewcraft.dto.UpdateFinishedGoodLotPortionDto;
import io.company.brewcraft.dto.UpdateMaterialPortionDto;
import io.company.brewcraft.dto.UpdateMixturePortionDto;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodLotMapperTest {
    private FinishedGoodLotMapper finishedGoodMapper;

    @BeforeEach
    public void init() {
        finishedGoodMapper = FinishedGoodLotMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddFinishedGoodLotDto dto = new AddFinishedGoodLotDto(
            5L,
            List.of(new AddMixturePortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(4)))),
            List.of(new AddMaterialPortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1))),
            List.of(new AddFinishedGoodLotPortionDto(10L, new QuantityDto("each", BigDecimal.valueOf(100)))),
            new QuantityDto("each", BigDecimal.valueOf(100)),
            LocalDateTime.of(1995, 1, 1, 1, 1)
        );

        FinishedGoodLot finishedGood = finishedGoodMapper.fromDto(dto);

        FinishedGoodLot expectedFinishedGood = new FinishedGoodLot(
            null,
            new Sku(5L),
            List.of(new FinishedGoodLotMixturePortion(null, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGoodLot(), null, null, null, null)),
            List.of(new FinishedGoodLotMaterialPortion(null, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGoodLot(), LocalDateTime.of(1999, 1, 1, 1, 1), null, null, null)),
            List.of(new FinishedGoodLotFinishedGoodLotPortion(null, new FinishedGoodLot(10L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(), null, null, null)),
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            null,
            null,
            null
            );

        assertEquals(expectedFinishedGood, finishedGood);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateFinishedGoodLotDto dto = new UpdateFinishedGoodLotDto(
            1L,
            5L,
            List.of(new UpdateMixturePortionDto(1L, 8L, new QuantityDto("kg", BigDecimal.valueOf(4)), 1)),
            List.of(new UpdateMaterialPortionDto(2L, 8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
            List.of(new UpdateFinishedGoodLotPortionDto(3L, 10L, new QuantityDto("each", BigDecimal.valueOf(100)), 1)),
            new QuantityDto("each", BigDecimal.valueOf(100)),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            1
        );

        FinishedGoodLot finishedGood = finishedGoodMapper.fromDto(dto);

        FinishedGoodLot expectedFinishedGood = new FinishedGoodLot(
            1L,
            new Sku(5L),
            List.of(new FinishedGoodLotMixturePortion(1L, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), null, null, null, 1)),
            List.of(new FinishedGoodLotMaterialPortion(2L, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), null, null, 1)),
            List.of(new FinishedGoodLotFinishedGoodLotPortion(3L, new FinishedGoodLot(10L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(10L), null, null, 1)),
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            null,
            null,
            1
        );

        assertEquals(expectedFinishedGood, finishedGood);
    }

    @Test
    public void testToDto_ReturnsDto() {
        FinishedGoodLot finishedGood = new FinishedGoodLot(
            1L,
            new Sku(5L),
            List.of(new FinishedGoodLotMixturePortion(6L, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
            List.of(new FinishedGoodLotMaterialPortion(7L, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGoodLot(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
            List.of(new FinishedGoodLotFinishedGoodLotPortion(8L, new FinishedGoodLot(10L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(10L), null, null, 1)),
            Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            LocalDateTime.of(2019, 1, 2, 3, 4),
            LocalDateTime.of(2020, 1, 2, 3, 4),
            1
        );

        FinishedGoodLotDto dto = finishedGoodMapper.toDto(finishedGood);

        FinishedGoodLotDto expectedDto = new FinishedGoodLotDto(
            1L,
            new SkuDto(5L),
            List.of(new MixturePortionDto(6L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)),
            List.of(new MaterialPortionDto(7L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
            List.of(new FinishedGoodLotPortionDto(8L, new FinishedGoodLotDto(10L), new QuantityDto("each", BigDecimal.valueOf(100)), 1)),
            new QuantityDto("each", BigDecimal.valueOf(100)),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            1
        );

        assertEquals(expectedDto, dto);
    }
}
