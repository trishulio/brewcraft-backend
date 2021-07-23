package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MixtureDtoTest {

	private MixtureDto mixtureDto;

	@BeforeEach
	public void init() {
		mixtureDto = new MixtureDto();
	}

	@Test
	public void testConstructor() {
		Long id = 1L;
		Long parentMixtureId = 2L;
		QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
		FacilityEquipmentDto equipmentDto = new FacilityEquipmentDto(3L);
		BrewStageDto brewStageDto = new BrewStageDto(4L);
		List<MaterialPortionDto> materialPortions = List.of(new MaterialPortionDto(5L));
		List<MixtureRecordingDto> recordedMeasures = List.of(new MixtureRecordingDto(6L));
		Integer version = 1;

		MixtureDto mixtureDto = new MixtureDto(id, parentMixtureId, quantity, equipmentDto, brewStageDto,
				materialPortions, recordedMeasures, version);

		assertEquals(1L, mixtureDto.getId());
		assertEquals(2L, mixtureDto.getParentMixtureId());
		assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), mixtureDto.getQuantity());
		assertEquals(new FacilityEquipmentDto(3L), mixtureDto.getEquipment());
		assertEquals(new BrewStageDto(4L), mixtureDto.getBrewStage());
		assertEquals(List.of(new MaterialPortionDto(5L)), mixtureDto.getMaterialPortions());
		assertEquals(List.of(new MixtureRecordingDto(6L)), mixtureDto.getRecordedMeasures());
		assertEquals(1, mixtureDto.getVersion());
	}

	@Test
	public void testGetSetId() {
		mixtureDto.setId(1L);
		assertEquals(1L, mixtureDto.getId());
	}

	@Test
	public void testGetSetParentMixtureId() {
		mixtureDto.setParentMixtureId(2L);
		assertEquals(2L, mixtureDto.getParentMixtureId());
	}

	@Test
	public void testGetSetQuantity() {
		mixtureDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
		assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), mixtureDto.getQuantity());
	}

	@Test
	    public void testGetSetEquipment() {
	        mixtureDto.setEquipment(new FacilityEquipmentDto(3L));
	        assertEquals(new FacilityEquipmentDto(3L), mixtureDto.getEquipment());
	    }

	@Test
	public void testGetBrewStage() {
		mixtureDto.setBrewStage(new BrewStageDto(4L));
		assertEquals(new BrewStageDto(4L), mixtureDto.getBrewStage());
	}

	@Test
	public void testGetSetMaterialPortions() {
		mixtureDto.setMaterialPortions(List.of(new MaterialPortionDto(5L)));
		assertEquals(List.of(new MaterialPortionDto(5L)), mixtureDto.getMaterialPortions());
	}

	@Test
	public void testGetSetRecordedMeasures() {
		mixtureDto.setRecordedMeasures(List.of(new MixtureRecordingDto(6L)));
		assertEquals(List.of(new MixtureRecordingDto(6L)), mixtureDto.getRecordedMeasures());
	}

	@Test
	public void testGetSetVersion() {
		Integer version = 1;
		mixtureDto.setVersion(version);
		assertEquals(version, mixtureDto.getVersion());
	}

}
