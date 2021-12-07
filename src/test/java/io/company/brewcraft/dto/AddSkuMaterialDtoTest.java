package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddSkuMaterialDtoTest {

    private AddSkuMaterialDto addSkuMaterialDto;

    @BeforeEach
    public void init() {
        addSkuMaterialDto = new AddSkuMaterialDto();
    }

    @Test
    public void testConstructor() {
        Long materialId = 3L;
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));

        AddSkuMaterialDto addSkuMaterialDto = new AddSkuMaterialDto(materialId, quantity);

        assertEquals(3L, addSkuMaterialDto.getMaterialId());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addSkuMaterialDto.getQuantity());
    }

    @Test
    public void testGetSetMaterialId() {
        addSkuMaterialDto.setMaterialId(3L);
        assertEquals(3L, addSkuMaterialDto.getMaterialId());
    }

    @Test
    public void testGetSetQuantity() {
        addSkuMaterialDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addSkuMaterialDto.getQuantity());
    }
}
