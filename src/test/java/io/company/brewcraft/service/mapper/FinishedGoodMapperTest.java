package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddFinishedGoodDto;
import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.AddMixturePortionDto;
import io.company.brewcraft.dto.FinishedGoodDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.MixturePortionDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.dto.UpdateFinishedGoodDto;
import io.company.brewcraft.dto.UpdateMaterialPortionDto;
import io.company.brewcraft.dto.UpdateMixturePortionDto;
import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodMapperTest {

    private FinishedGoodMapper finishedGoodMapper;

    @BeforeEach
    public void init() {
        finishedGoodMapper = FinishedGoodMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddFinishedGoodDto dto = new AddFinishedGoodDto(
            5L,
            List.of(new AddMixturePortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(4)))),
            List.of(new AddMaterialPortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1))),
            LocalDateTime.of(1995, 1, 1, 1, 1)
        );

        FinishedGood finishedGood = finishedGoodMapper.fromDto(dto);

        FinishedGood expectedFinishedGood = new FinishedGood(
            null,
            new Sku(5L),
            List.of(new FinishedGoodMixturePortion(null, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGood(10L), null, null, null, null)),
            List.of(new FinishedGoodMaterialPortion(null, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGood(10L), LocalDateTime.of(1999, 1, 1, 1, 1), null, null, null)),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            null,
            null,
            null
            );

        assertEquals(expectedFinishedGood, finishedGood);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateFinishedGoodDto dto = new UpdateFinishedGoodDto(
            5L,
            List.of(new UpdateMixturePortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(4)), 1)),
            List.of(new UpdateMaterialPortionDto(8L, new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            1
        );

        FinishedGood finishedGood = finishedGoodMapper.fromDto(dto);

        FinishedGood expectedFinishedGood = new FinishedGood(
            null,
            new Sku(5L),
            List.of(new FinishedGoodMixturePortion(null, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGood(10L), null, null, null, 1)),
            List.of(new FinishedGoodMaterialPortion(null, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGood(10L), LocalDateTime.of(1999, 1, 1, 1, 1), null, null, 1)),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            null,
            null,
            1
        );

        assertEquals(expectedFinishedGood, finishedGood);
    }

    @Test
    public void testToDto_ReturnsDto() {
        FinishedGood finishedGood = new FinishedGood(
            1L,
            new Sku(5L),
            List.of(new FinishedGoodMixturePortion(6L, new Mixture(8L), Quantities.getQuantity(new BigDecimal("4"), SupportedUnits.KILOGRAM), new FinishedGood(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
            List.of(new FinishedGoodMaterialPortion(7L, new MaterialLot(8L), Quantities.getQuantity(new BigDecimal("5"), SupportedUnits.KILOGRAM), new FinishedGood(10L), LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.of(2000, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1), 1)),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            LocalDateTime.of(2019, 1, 2, 3, 4),
            LocalDateTime.of(2020, 1, 2, 3, 4),
            1
        );

        FinishedGoodDto dto = finishedGoodMapper.toDto(finishedGood);

        FinishedGoodDto expectedDto = new FinishedGoodDto(
            1L,
            new SkuDto(5L),
            List.of(new MixturePortionDto(6L, new MixtureDto(8L), new QuantityDto("kg", BigDecimal.valueOf(4)), 1)),
            List.of(new MaterialPortionDto(7L, new MaterialLotDto(8L), new QuantityDto("kg", BigDecimal.valueOf(5)), LocalDateTime.of(1999, 1, 1, 1, 1), 1)),
            LocalDateTime.of(1995, 1, 1, 1, 1),
            1
        );

        assertEquals(expectedDto, dto);
    }
}
