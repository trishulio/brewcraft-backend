package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.util.SupportedUnits;

public class FinishedGoodInventoryAggregationDtoTest {
    private FinishedGoodInventoryAggregationDto finishedGoodInventoryAggregationDto;

    @BeforeEach
    public void init() {
        finishedGoodInventoryAggregationDto = new FinishedGoodInventoryAggregationDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        SkuDto skuDto = new SkuDto(1L);
        QuantityDto quantity = new QuantityDto("each", BigDecimal.valueOf(50));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);

        FinishedGoodInventoryAggregationDto finishedGoodInventoryAggregationDto = new FinishedGoodInventoryAggregationDto(id, skuDto, quantity, packagedOn);

        assertEquals(1L, finishedGoodInventoryAggregationDto.getId());
        assertEquals(new SkuDto(1L), finishedGoodInventoryAggregationDto.getSku());
        assertEquals(new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(50L)), finishedGoodInventoryAggregationDto.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventoryAggregationDto.getPackagedOn());
    }

    @Test
    public void testGetSetId() {
        finishedGoodInventoryAggregationDto.setId(1L);
        assertEquals(1L, finishedGoodInventoryAggregationDto.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodInventoryAggregationDto.setSku(new SkuDto(1L));
        assertEquals(new SkuDto(1L), finishedGoodInventoryAggregationDto.getSku());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodInventoryAggregationDto.setQuantity(new QuantityDto("each", BigDecimal.valueOf(50)));
        assertEquals(new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(50L)), finishedGoodInventoryAggregationDto.getQuantity());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGoodInventoryAggregationDto.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventoryAggregationDto.getPackagedOn());
    }
}
