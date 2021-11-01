package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

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
        int version = 1;

        FinishedGoodDto finishedGood = new FinishedGoodDto(id, sku, mixturePortions, materialPortions, version);

        assertEquals(1L, finishedGood.getId());
        assertEquals(new SkuDto(2L), finishedGood.getSku());
        assertEquals(List.of(new MixturePortionDto(5L)), finishedGood.getMixturePortions());
        assertEquals(List.of(new MaterialPortionDto(6L)), finishedGood.getMaterialPortions());
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
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGood.setVersion(version);
        assertEquals(version, finishedGood.getVersion());
    }
}
