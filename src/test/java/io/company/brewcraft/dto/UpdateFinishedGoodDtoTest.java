package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateFinishedGoodDtoTest {

    private UpdateFinishedGoodDto finishedGood;

    @BeforeEach
    public void init() {
        finishedGood = new UpdateFinishedGoodDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Long skuId = 2L;
        List<UpdateMixturePortionDto> mixturePortions = List.of(new UpdateMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1));
        List<UpdateMaterialPortionDto> materialPortions = List.of(new UpdateMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        Integer version = 1;

        UpdateFinishedGoodDto finishedGood = new UpdateFinishedGoodDto(id, skuId, mixturePortions, materialPortions, packagedOn, version);

        assertEquals(1L, finishedGood.getId());
        assertEquals(2L, finishedGood.getSkuId());
        assertEquals(List.of(new UpdateMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)), finishedGood.getMixturePortions());
        assertEquals(List.of(new UpdateMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), finishedGood.getMaterialPortions());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
        assertEquals(1, finishedGood.getVersion());
    }

    @Test
    public void testGetSetSkuId() {
        finishedGood.setSkuId(3L);
        assertEquals(3L, finishedGood.getSkuId());
    }

    @Test
    public void testGetSetMixturePortions() {
        finishedGood.setMixturePortions(List.of(new UpdateMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)));
        assertEquals(List.of(new UpdateMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), 1)), finishedGood.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGood.setMaterialPortions(List.of(new UpdateMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1)));
        assertEquals(List.of(new UpdateMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4), 1)), finishedGood.getMaterialPortions());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGood.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGood.setVersion(version);
        assertEquals(version, finishedGood.getVersion());
    }
}
