package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinishedGoodLotDtoTest {
    private FinishedGoodLotDto finishedGoodLot;

    @BeforeEach
    public void init() {
        finishedGoodLot = new FinishedGoodLotDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        SkuDto sku = new SkuDto(2L);
        List<MixturePortionDto> mixturePortions = List.of(new MixturePortionDto(5L));
        List<MaterialPortionDto> materialPortions = List.of(new MaterialPortionDto(6L));
        List<FinishedGoodLotPortionDto> finishedGoodLotPortions = List.of(new FinishedGoodLotPortionDto(1L));
        QuantityDto quantity = new QuantityDto("each", BigDecimal.valueOf(100.0));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        int version = 1;

        FinishedGoodLotDto finishedGoodLot = new FinishedGoodLotDto(id, sku, mixturePortions, materialPortions, finishedGoodLotPortions, quantity, packagedOn, version);

        assertEquals(1L, finishedGoodLot.getId());
        assertEquals(new SkuDto(2L), finishedGoodLot.getSku());
        assertEquals(List.of(new MixturePortionDto(5L)), finishedGoodLot.getMixturePortions());
        assertEquals(List.of(new MaterialPortionDto(6L)), finishedGoodLot.getMaterialPortions());
        assertEquals(List.of(new FinishedGoodLotPortionDto(1L)), finishedGoodLot.getFinishedGoodLotPortions());
        assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodLot.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodLot.getPackagedOn());
        assertEquals(1, finishedGoodLot.getVersion());
    }

    @Test
    public void testGetSetId() {
        finishedGoodLot.setId(1L);
        assertEquals(1L, finishedGoodLot.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodLot.setSku(new SkuDto(3L));
        assertEquals(new SkuDto(3L), finishedGoodLot.getSku());
    }

    @Test
    public void testGetSetMixturePortions() {
        finishedGoodLot.setMixturePortions(List.of(new MixturePortionDto(5L)));
        assertEquals(List.of(new MixturePortionDto(5L)), finishedGoodLot.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGoodLot.setMaterialPortions(List.of(new MaterialPortionDto(6L)));
        assertEquals(List.of(new MaterialPortionDto(6L)), finishedGoodLot.getMaterialPortions());
    }

    @Test
    public void testGetSetFinishedGoodLotPortions() {
        finishedGoodLot.setFinishedGoodLotPortions(List.of(new FinishedGoodLotPortionDto(1L)));
        assertEquals(List.of(new FinishedGoodLotPortionDto(1L)), finishedGoodLot.getFinishedGoodLotPortions());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodLot.setQuantity(new QuantityDto("each", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodLot.getQuantity());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGoodLot.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodLot.getPackagedOn());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGoodLot.setVersion(version);
        assertEquals(version, finishedGoodLot.getVersion());
    }
}
