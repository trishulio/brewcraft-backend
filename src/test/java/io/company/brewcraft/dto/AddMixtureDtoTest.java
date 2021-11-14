package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

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
        Set<Long> parentMixtureIds = Set.of(2L);
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
        Long equipmentId = 3L;
        Long brewStageId = 4L;

        AddMixtureDto addMixtureDto = new AddMixtureDto(parentMixtureIds, quantity, equipmentId, brewStageId);

        assertEquals(Set.of(2L), addMixtureDto.getParentMixtureIds());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addMixtureDto.getQuantity());
        assertEquals(3L, addMixtureDto.getEquipmentId());
        assertEquals(4L, addMixtureDto.getBrewStageId());
    }

    @Test
    public void testGetSetParentMixtureIds() {
        addMixtureDto.setParentMixtureIds(Set.of(2L));
        assertEquals(Set.of(2L), addMixtureDto.getParentMixtureIds());
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
