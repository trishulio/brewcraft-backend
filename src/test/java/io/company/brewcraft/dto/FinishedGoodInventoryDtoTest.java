package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

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
        SkuDto skuDto = new SkuDto(1L);
        Long quantity = 50L;

        FinishedGoodInventoryDto finishedGoodInventoryDto = new FinishedGoodInventoryDto(skuDto, quantity);

        assertEquals(new SkuDto(1L), finishedGoodInventoryDto.getSku());
        assertEquals(50L, finishedGoodInventoryDto.getQuantity());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodInventoryDto.setSku(new SkuDto(1L));
        assertEquals(new SkuDto(1L), finishedGoodInventoryDto.getSku());
    }
    
    @Test
    public void testGetSetQuantity() {
        finishedGoodInventoryDto.setQuantity(50L);
        assertEquals(50L, finishedGoodInventoryDto.getQuantity());
    }
}
