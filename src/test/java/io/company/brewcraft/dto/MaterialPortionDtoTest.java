package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialPortionDtoTest {

    private MaterialPortionDto materialPortionDto;

    @BeforeEach
    public void init() {
        materialPortionDto = new MaterialPortionDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        MaterialLotDto materialLotDto = new MaterialLotDto(3L);
        QuantityDto quantityDto = new QuantityDto("kg", new BigDecimal("100"));
        MixtureDto mixtureDto = new MixtureDto(2L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        MaterialPortionDto materialPortionDto = new MaterialPortionDto(id, materialLotDto, quantityDto, mixtureDto, addedAt, version);

        assertEquals(1L, materialPortionDto.getId());
        assertEquals(new MaterialLotDto(3L), materialPortionDto.getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), materialPortionDto.getQuantity());
        assertEquals(new MixtureDto(2L), materialPortionDto.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortionDto.getAddedAt());
        assertEquals(1, materialPortionDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        materialPortionDto.setId(1L);
        assertEquals(1L, materialPortionDto.getId());
    }

    @Test
    public void testGetSetMaterialLot() {
        materialPortionDto.setMaterialLot(new MaterialLotDto(3L));

        assertEquals(new MaterialLotDto(3L), materialPortionDto.getMaterialLot());
    }

    @Test
    public void testGetSetQuantity() {
        materialPortionDto.setQuantity(new QuantityDto("kg", new BigDecimal("100")));
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), materialPortionDto.getQuantity());
    }
    
    @Test
    public void testGetSetMixture() {
        materialPortionDto.setMixture(new MixtureDto(2L));
        assertEquals(new MixtureDto(2L), materialPortionDto.getMixture());
    }

    @Test
    public void testGetSetAddedAt() {
        materialPortionDto.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortionDto.getAddedAt());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        materialPortionDto.setVersion(version);
        assertEquals(version, materialPortionDto.getVersion());
    }

}
