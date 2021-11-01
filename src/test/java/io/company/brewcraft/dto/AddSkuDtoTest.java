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
        String name = "testName";
        String description = "testDescription";
        Long productId = 2L;
        List<AddSkuMaterialDto> materials = List.of(new AddSkuMaterialDto(3L));
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));

        AddSkuDto addSkuDto = new AddSkuDto(name, description, productId, materials, quantity);

        assertEquals("testName", addSkuDto.getName());
        assertEquals("testDescription", addSkuDto.getDescription());
        assertEquals(2L, addSkuDto.getProductId());
        assertEquals(List.of(new AddSkuMaterialDto(3L)), addSkuDto.getMaterials());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), addSkuDto.getQuantity());
    }

    @Test
    public void testGetSetName() {
        addSkuDto.setName("testName");
        assertEquals("testName", addSkuDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        addSkuDto.setDescription("testDescription");
        assertEquals("testDescription", addSkuDto.getDescription());
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
