package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddSkuDtoTest {

    private AddSkuDto addSkuDto;

    @BeforeEach
    public void init() {
        addSkuDto = new AddSkuDto();
    }

    @Test
    public void testConstructor() {
        Long productId = 2L;
        List<AddSkuMaterialDto> materials = List.of(new AddSkuMaterialDto(3L));
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));

        AddSkuDto addSkuDto = new AddSkuDto(productId, materials, quantity);

        assertEquals(2L, addSkuDto.getProductId());
        assertEquals(List.of(new AddSkuMaterialDto(3L)), addSkuDto.getMaterials());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addSkuDto.getQuantity());
    }

    @Test
    public void testGetSetProductId() {
        addSkuDto.setProductId(2L);
        assertEquals(2L, addSkuDto.getProductId());
    }

    @Test
    public void testGetSetMaterials() {
        addSkuDto.setMaterials(List.of(new AddSkuMaterialDto(3L)));
        assertEquals(List.of(new AddSkuMaterialDto(3L)), addSkuDto.getMaterials());
    }

    @Test
    public void testGetSetQuantity() {
        addSkuDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addSkuDto.getQuantity());
    }

}
