package io.company.brewcraft.service.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.dto.SkuDto;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.Sku;

public class FinishedGoodInventoryMapperTest {

    private FinishedGoodInventoryMapper finishedGoodInventoryMapper;

    @BeforeEach
    public void init() {
        finishedGoodInventoryMapper = FinishedGoodInventoryMapper.INSTANCE;
    }

    @Test
    public void testToDto_ReturnsDto() {
        FinishedGoodInventory finishedGoodInventory = new FinishedGoodInventory(1L, new Sku(2L), 50L);
        FinishedGoodInventoryDto dto = finishedGoodInventoryMapper.toDto(finishedGoodInventory);

        assertEquals(new FinishedGoodInventoryDto(1L, new SkuDto(2L), 50L), dto);
    }
}
