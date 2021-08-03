package io.company.brewcraft.service.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.company.brewcraft.dto.AddMixtureDto;
import io.company.brewcraft.dto.BrewStageDto;
import io.company.brewcraft.dto.FacilityEquipmentDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateMixtureDto;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
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
		AddMixtureDto dto = new AddMixtureDto(2L, new QuantityDto("hl", BigDecimal.valueOf(100)), 3L, 4L);
		
		Mixture mixture = mixtureMapper.fromDto(dto);

		Mixture parentMixture = new Mixture(2L);
		parentMixture.addChildMixture(mixture);
		Mixture expectedMixture = new Mixture(null, parentMixture, null, Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), new Equipment(3L), null, null, new BrewStage(4L), null, null, null);

		assertEquals(expectedMixture, mixture);
	}

	@Test
	public void testFromUpdateDto_ReturnsEntity() {
		UpdateMixtureDto dto = new UpdateMixtureDto(2L, new QuantityDto("hl", BigDecimal.valueOf(100)), 3L, 4L, 1);
		
		Mixture mixture = mixtureMapper.fromDto(dto);

		Mixture parentMixture = new Mixture(2L);
		parentMixture.addChildMixture(mixture);
		Mixture expectedMixture = new Mixture(null, parentMixture, null, Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), new Equipment(3L), null, null, new BrewStage(4L), null, null, 1);

		assertEquals(expectedMixture, mixture);
	}

	@Test
	public void testToDto_ReturnsDto() {
		Mixture mixture = new Mixture(1L, new Mixture(2L), null, Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), new Equipment(3L), List.of(new MaterialPortion(6L)), List.of(new MixtureRecording(7L)), new BrewStage(4L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1);
		
		MixtureDto dto = mixtureMapper.toDto(mixture);

		assertEquals(new MixtureDto(1L, 2L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), new FacilityEquipmentDto(3L), new BrewStageDto(4L), List.of(new MaterialPortionDto(6L)), List.of(new MixtureRecordingDto(7L, 1L)), 1), dto);
	}

}
