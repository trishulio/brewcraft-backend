package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddMixtureMaterialPortionDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MixtureMaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateMixtureMaterialPortionDto;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MixtureMaterialPortionMapperTest {
    private MixtureMaterialPortionMapper materialPortionMapper;

    @BeforeEach
    public void init() {
        materialPortionMapper = MixtureMaterialPortionMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddMixtureMaterialPortionDto addMaterialPortionDto = new AddMixtureMaterialPortionDto(2L, new QuantityDto("g", new BigDecimal("100")), 1L, LocalDateTime.of(2018, 1, 2, 3, 4));

        MixtureMaterialPortion materialPortion = materialPortionMapper.fromDto(addMaterialPortionDto);

        MixtureMaterialPortion expectedMaterialPortion = new MixtureMaterialPortion(null, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.GRAM), new Mixture(1L), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, null);

        assertEquals(expectedMaterialPortion, materialPortion);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateMixtureMaterialPortionDto updateMaterialPortionDto = new UpdateMixtureMaterialPortionDto(5L, 1L, new QuantityDto("g", new BigDecimal("100")), 2L, LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureMaterialPortion materialPortion = materialPortionMapper.fromDto(updateMaterialPortionDto);

        MixtureMaterialPortion expectedMaterialPortion = new MixtureMaterialPortion(5L, new MaterialLot(1L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.GRAM), new Mixture(2L), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, 1);

        assertEquals(expectedMaterialPortion, materialPortion);
    }

    @Test
    public void testToDto_ReturnsDto() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.GRAM), new Mixture(3L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        MixtureMaterialPortionDto dto = materialPortionMapper.toDto(materialPortion);

        MixtureMaterialPortionDto expectedMaterialPortionDto = new MixtureMaterialPortionDto(1L, new MaterialLotDto(2L), new QuantityDto("g", new BigDecimal("100")), new MixtureDto(3L), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        assertEquals(expectedMaterialPortionDto, dto);
    }
}
