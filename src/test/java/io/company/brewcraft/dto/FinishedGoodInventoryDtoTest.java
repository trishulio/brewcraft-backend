package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinishedGoodInventoryDtoTest {

    private FinishedGoodInventoryDto finishedGoodInventoryDto;

    @BeforeEach
    public void init() {
        finishedGoodInventoryDto = new FinishedGoodInventoryDto();
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

        FinishedGoodInventoryDto finishedGoodInventoryDto = new FinishedGoodInventoryDto(id, sku, mixturePortions, materialPortions, finishedGoodLotPortions, packagedOn, quantity);

        assertEquals(1L, finishedGoodInventoryDto.getId());
        assertEquals(new SkuDto(2L), finishedGoodInventoryDto.getSku());
        assertEquals(List.of(new MixturePortionDto(5L)), finishedGoodInventoryDto.getMixturePortions());
        assertEquals(List.of(new MaterialPortionDto(6L)), finishedGoodInventoryDto.getMaterialPortions());
        assertEquals(List.of(new FinishedGoodLotPortionDto(1L)), finishedGoodInventoryDto.getFinishedGoodLotPortions());
        assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodInventoryDto.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventoryDto.getPackagedOn());
    }

    @Test
    public void testGetSetId() {
        finishedGoodInventoryDto.setId(1L);
        assertEquals(1L, finishedGoodInventoryDto.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodInventoryDto.setSku(new SkuDto(3L));
        assertEquals(new SkuDto(3L), finishedGoodInventoryDto.getSku());
    }

    @Test
    public void testGetSetMixturePortions() {
        finishedGoodInventoryDto.setMixturePortions(List.of(new MixturePortionDto(5L)));
        assertEquals(List.of(new MixturePortionDto(5L)), finishedGoodInventoryDto.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGoodInventoryDto.setMaterialPortions(List.of(new MaterialPortionDto(6L)));
        assertEquals(List.of(new MaterialPortionDto(6L)), finishedGoodInventoryDto.getMaterialPortions());
    }

    @Test
    public void testGetSetFinishedGoodLotPortions() {
        finishedGoodInventoryDto.setFinishedGoodLotPortions(List.of(new FinishedGoodLotPortionDto(1L)));
        assertEquals(List.of(new FinishedGoodLotPortionDto(1L)), finishedGoodInventoryDto.getFinishedGoodLotPortions());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodInventoryDto.setQuantity(new QuantityDto("each", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("each", BigDecimal.valueOf(100.0)), finishedGoodInventoryDto.getQuantity());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGoodInventoryDto.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventoryDto.getPackagedOn());
    }
}
