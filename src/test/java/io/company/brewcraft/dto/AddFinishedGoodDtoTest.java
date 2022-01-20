package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddFinishedGoodDtoTest {

    private AddFinishedGoodDto finishedGood;

    @BeforeEach
    public void init() {
        finishedGood = new AddFinishedGoodDto();
    }

    @Test
    public void testConstructor() {
        Long skuId = 2L;
        List<AddMixturePortionDto> mixturePortions = List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0))));
        List<AddMaterialPortionDto> materialPortions = List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4)));
        List<Long> childFinishedGoodsIds = List.of(1L, 2L);
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);

        AddFinishedGoodDto finishedGood = new AddFinishedGoodDto(skuId, mixturePortions, materialPortions, childFinishedGoodsIds, packagedOn);

        assertEquals(2L, finishedGood.getSkuId());
        assertEquals(List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))), finishedGood.getMixturePortions());
        assertEquals(List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4))), finishedGood.getMaterialPortions());
        assertEquals(List.of(1L, 2L), finishedGood.getChildFinishedGoodIds());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
    }

    @Test
    public void testGetSetSkuId() {
        finishedGood.setSkuId(3L);
        assertEquals(3L, finishedGood.getSkuId());
    }

    @Test
    public void testGetSetMixturePortions() {
        finishedGood.setMixturePortions(List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))));
        assertEquals(List.of(new AddMixturePortionDto(5L, new QuantityDto("hl", BigDecimal.valueOf(100.0)))), finishedGood.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGood.setMaterialPortions(List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4))));
        assertEquals(List.of(new AddMaterialPortionDto(6L, new QuantityDto("hl", BigDecimal.valueOf(100.0)), LocalDateTime.of(2019, 1, 2, 3, 4))), finishedGood.getMaterialPortions());
    }

    @Test
    public void testGetSetChildFinishedGoodIds() {
        finishedGood.setChildFinishedGoodIds(List.of(1L, 2L));
        assertEquals(List.of(1L, 2L), finishedGood.getChildFinishedGoodIds());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGood.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
    }
}
