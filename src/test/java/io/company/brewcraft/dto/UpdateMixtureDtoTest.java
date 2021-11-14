package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMixtureDtoTest {

    private UpdateMixtureDto updateMixtureDto;

    @BeforeEach
    public void init() {
        updateMixtureDto = new UpdateMixtureDto();
    }

    @Test
    public void testConstructor() {
        Set<Long> parentMixtureIds = Set.of(2L);
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
        Long equipmentId = 3L;
        Long brewStageId = 4L;
        Integer version = 1;

        UpdateMixtureDto updateMixtureDto = new UpdateMixtureDto(parentMixtureIds, quantity, equipmentId, brewStageId, version);

        assertEquals(Set.of(2L), updateMixtureDto.getParentMixtureIds());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), updateMixtureDto.getQuantity());
        assertEquals(3L, updateMixtureDto.getEquipmentId());
        assertEquals(4L, updateMixtureDto.getBrewStageId());
        assertEquals(1, updateMixtureDto.getVersion());
    }

    @Test
    public void testGetSetParentMixtureIds() {
        updateMixtureDto.setParentMixtureIds(Set.of(2L));
        assertEquals(Set.of(2L), updateMixtureDto.getParentMixtureIds());
    }

    @Test
    public void testGetSetQuantity() {
        updateMixtureDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), updateMixtureDto.getQuantity());
    }

    @Test
        public void testGetSetEquipmentId() {
            updateMixtureDto.setEquipmentId(3L);
            assertEquals(3L, updateMixtureDto.getEquipmentId());
        }

    @Test
    public void testGetBrewStageId() {
        updateMixtureDto.setBrewStageId(4L);
        assertEquals(4L, updateMixtureDto.getBrewStageId());
    }

    @Test
    public void testGetSetVersion() {
        updateMixtureDto.setVersion(1);
        assertEquals(1, updateMixtureDto.getVersion());
    }

}
