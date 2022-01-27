package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.util.SupportedUnits;

public class FinishedGoodInventoryDtoTest {

    private FinishedGoodInventoryDto finishedGoodInventoryDto;

    @BeforeEach
    public void init() {
        finishedGoodInventoryDto = new FinishedGoodInventoryDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        SkuDto skuDto = new SkuDto(1L);
        Long quantity = 50L;

        FinishedGoodInventoryDto finishedGoodInventoryDto = new FinishedGoodInventoryDto(id, skuDto, quantity);

        assertEquals(1L, finishedGoodInventoryDto.getId());
        assertEquals(new SkuDto(1L), finishedGoodInventoryDto.getSku());
        assertEquals(new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(50L)), finishedGoodInventoryDto.getQuantity());
    }

    @Test
    public void testGetSetId() {
        finishedGoodInventoryDto.setId(1L);
        assertEquals(1L, finishedGoodInventoryDto.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodInventoryDto.setSku(new SkuDto(1L));
        assertEquals(new SkuDto(1L), finishedGoodInventoryDto.getSku());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodInventoryDto.setQuantity(50L);
        assertEquals(new QuantityDto(SupportedUnits.EACH.getSymbol(), new BigDecimal(50L)), finishedGoodInventoryDto.getQuantity());
    }
}
