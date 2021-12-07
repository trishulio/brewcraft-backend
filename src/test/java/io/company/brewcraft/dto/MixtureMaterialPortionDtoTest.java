package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MixtureMaterialPortionDtoTest {

    private MixtureMaterialPortionDto mixtureMaterialPortionDto;

    @BeforeEach
    public void init() {
        mixtureMaterialPortionDto = new MixtureMaterialPortionDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        MaterialLotDto materialLotDto = new MaterialLotDto(3L);
        QuantityDto quantityDto = new QuantityDto("kg", new BigDecimal("100"));
        MixtureDto mixtureDto = new MixtureDto(2L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        Integer version = 1;

        MixtureMaterialPortionDto mixtureMaterialPortionDto = new MixtureMaterialPortionDto(id, materialLotDto, quantityDto, mixtureDto, addedAt, version);

        assertEquals(1L, mixtureMaterialPortionDto.getId());
        assertEquals(new MaterialLotDto(3L), mixtureMaterialPortionDto.getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), mixtureMaterialPortionDto.getQuantity());
        assertEquals(new MixtureDto(2L), mixtureMaterialPortionDto.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureMaterialPortionDto.getAddedAt());
        assertEquals(1, mixtureMaterialPortionDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        mixtureMaterialPortionDto.setId(1L);
        assertEquals(1L, mixtureMaterialPortionDto.getId());
    }

    @Test
    public void testGetSetMaterialLot() {
        mixtureMaterialPortionDto.setMaterialLot(new MaterialLotDto(3L));

        assertEquals(new MaterialLotDto(3L), mixtureMaterialPortionDto.getMaterialLot());
    }

    @Test
    public void testGetSetQuantity() {
        mixtureMaterialPortionDto.setQuantity(new QuantityDto("kg", new BigDecimal("100")));
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), mixtureMaterialPortionDto.getQuantity());
    }

    @Test
    public void testGetSetMixture() {
        mixtureMaterialPortionDto.setMixture(new MixtureDto(2L));
        assertEquals(new MixtureDto(2L), mixtureMaterialPortionDto.getMixture());
    }

    @Test
    public void testGetSetAddedAt() {
        mixtureMaterialPortionDto.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureMaterialPortionDto.getAddedAt());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        mixtureMaterialPortionDto.setVersion(version);
        assertEquals(version, mixtureMaterialPortionDto.getVersion());
    }
}
