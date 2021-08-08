package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddMixtureDtoTest {
	
	private AddMixtureDto addMixtureDto;

	@BeforeEach
	public void init() {
		addMixtureDto = new AddMixtureDto();
	}

	@Test
	public void testConstructor() {
		Long parentMixtureId = 2L;
		QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
		Long equipmentId = 3L;
		Long brewStageId = 4L;

		AddMixtureDto addMixtureDto = new AddMixtureDto(parentMixtureId, quantity, equipmentId, brewStageId);

		assertEquals(2L, addMixtureDto.getParentMixtureId());
		assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addMixtureDto.getQuantity());
		assertEquals(3L, addMixtureDto.getEquipmentId());
		assertEquals(4L, addMixtureDto.getBrewStageId());
	}

	@Test
	public void testGetSetParentMixtureId() {
		addMixtureDto.setParentMixtureId(2L);
		assertEquals(2L, addMixtureDto.getParentMixtureId());
	}

	@Test
	public void testGetSetQuantity() {
		addMixtureDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
		assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addMixtureDto.getQuantity());
	}

	@Test
	    public void testGetSetEquipmentId() {
	        addMixtureDto.setEquipmentId(3L);
	        assertEquals(3L, addMixtureDto.getEquipmentId());
	    }

	@Test
	public void testGetBrewStageId() {
		addMixtureDto.setBrewStageId(4L);
		assertEquals(4L, addMixtureDto.getBrewStageId());
	}

}
