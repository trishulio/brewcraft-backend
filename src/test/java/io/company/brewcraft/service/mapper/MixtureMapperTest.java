package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.AddMixtureDto;
import io.company.brewcraft.dto.BrewStageDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateMixtureDto;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MixtureMapperTest {

    private MixtureMapper mixtureMapper;

    @BeforeEach
    public void init() {
        mixtureMapper = MixtureMapper.INSTANCE;
    }

    @Test
    public void testFromAddDto_ReturnsEntity() {
        AddMixtureDto dto = new AddMixtureDto(Set.of(2L), new QuantityDto("hl", BigDecimal.valueOf(100)), 3L, 4L);

        Mixture mixture = mixtureMapper.fromDto(dto);

        Mixture expectedMixture = new Mixture(null, List.of(new Mixture(2L)), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), new Equipment(3L), null, null, new BrewStage(4L), null, null, null);

        assertEquals(expectedMixture, mixture);
    }

    @Test
    public void testFromUpdateDto_ReturnsEntity() {
        UpdateMixtureDto dto = new UpdateMixtureDto(Set.of(2L), new QuantityDto("hl", BigDecimal.valueOf(100)), 3L, 4L, 1);

        Mixture mixture = mixtureMapper.fromDto(dto);

        Mixture expectedMixture = new Mixture(null, List.of(new Mixture(2L)), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), new Equipment(3L), null, null, new BrewStage(4L), null, null, 1);

        assertEquals(expectedMixture, mixture);
    }

    @Test
    public void testToDto_ReturnsDto() {
        Mixture mixture = new Mixture(1L, List.of(new Mixture(2L)), Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MixtureMaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);

        MixtureDto dto = mixtureMapper.toDto(mixture);

        assertEquals(new MixtureDto(1L, Set.of(2L), new QuantityDto("hl", BigDecimal.valueOf(100.0)), new FacilityEquipmentDto(3L), new BrewStageDto(4L), 1), dto);
    }

}
