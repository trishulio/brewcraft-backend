package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinishedGoodDtoTest {

    private FinishedGoodDto finishedGood;

    @BeforeEach
    public void init() {
        finishedGood = new FinishedGoodDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        SkuDto sku = new SkuDto(2L);
        List<MixturePortionDto> mixturePortions = List.of(new MixturePortionDto(5L));
        List<MaterialPortionDto> materialPortions = List.of(new MaterialPortionDto(6L));
        Long parentFinishedGoodId = 6L;
        List<FinishedGoodDto> childFinishedGoods = List.of(new FinishedGoodDto(1L));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        int version = 1;

        FinishedGoodDto finishedGood = new FinishedGoodDto(id, sku, mixturePortions, materialPortions, parentFinishedGoodId, childFinishedGoods, packagedOn, version);

        assertEquals(1L, finishedGood.getId());
        assertEquals(new SkuDto(2L), finishedGood.getSku());
        assertEquals(List.of(new MixturePortionDto(5L)), finishedGood.getMixturePortions());
        assertEquals(List.of(new MaterialPortionDto(6L)), finishedGood.getMaterialPortions());
        assertEquals(6L, finishedGood.getParentFinishedGoodId());
        assertEquals(List.of(new FinishedGoodDto(1L)), finishedGood.getChildFinishedGoods());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
        assertEquals(1, finishedGood.getVersion());
    }

    @Test
    public void testGetSetId() {
        finishedGood.setId(1L);
        assertEquals(1L, finishedGood.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGood.setSku(new SkuDto(3L));
        assertEquals(new SkuDto(3L), finishedGood.getSku());
    }

    @Test
    public void testGetSetMixturePortions() {
        finishedGood.setMixturePortions(List.of(new MixturePortionDto(5L)));
        assertEquals(List.of(new MixturePortionDto(5L)), finishedGood.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGood.setMaterialPortions(List.of(new MaterialPortionDto(6L)));
        assertEquals(List.of(new MaterialPortionDto(6L)), finishedGood.getMaterialPortions());
    }

    @Test
    public void testGetSetParentFinishedGoodId() {
        finishedGood.setParentFinishedGoodId(1L);
        assertEquals(1L, finishedGood.getParentFinishedGoodId());
    }

    @Test
    public void testGetSetChildFinishedGoods() {
        finishedGood.setChildFinishedGoods(List.of(new FinishedGoodDto(1L)));;
        assertEquals(List.of(new FinishedGoodDto(1L)), finishedGood.getChildFinishedGoods());
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
