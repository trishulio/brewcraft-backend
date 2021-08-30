package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateMaterialPortionDto;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MaterialPortionMapperTest {

    private MaterialPortionMapper materialPortionMapper;

    @BeforeEach
    public void init() {
        materialPortionMapper = MaterialPortionMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddMaterialPortionDto addMaterialPortionDto = new AddMaterialPortionDto(2L, new QuantityDto("kg", new BigDecimal("100")), 1L, LocalDateTime.of(2018, 1, 2, 3, 4));

        MaterialPortion materialPortion = materialPortionMapper.fromDto(addMaterialPortionDto);

        MaterialPortion expectedMaterialPortion = new MaterialPortion(null, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(1L), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, null);

        assertEquals(expectedMaterialPortion, materialPortion);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateMaterialPortionDto updateMaterialPortionDto = new UpdateMaterialPortionDto(1L, new QuantityDto("kg", new BigDecimal("100")), 2L, LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MaterialPortion materialPortion = materialPortionMapper.fromDto(updateMaterialPortionDto);

        MaterialPortion expectedMaterialPortion = new MaterialPortion(null, new MaterialLot(1L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(2L), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, 1);

        assertEquals(expectedMaterialPortion, materialPortion);
    }

    @Test
    public void testToDto_ReturnsDto() {
        MaterialPortion materialPortion = new MaterialPortion(1L, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(3L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        MaterialPortionDto dto = materialPortionMapper.toDto(materialPortion);
        
        MaterialPortionDto expectedMaterialPortionDto = new MaterialPortionDto(1L, new MaterialLotDto(2L), new QuantityDto("kg", new BigDecimal("100")), new MixtureDto(3L), LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        assertEquals(expectedMaterialPortionDto, dto);
    }

}
