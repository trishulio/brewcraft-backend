package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SkuDtoTest {

    private SkuDto skuDto;

    @BeforeEach
    public void init() {
        skuDto = new SkuDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDescription";
        ProductDto product = new ProductDto(2L);
        List<SkuMaterialDto> materials = List.of(new SkuMaterialDto(3L));
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
        int version = 1;

        SkuDto skuDto = new SkuDto(id, name, description, product, materials, quantity, version);

        assertEquals(1L, skuDto.getId());
        assertEquals("testName", skuDto.getName());
        assertEquals("testDescription", skuDto.getDescription());
        assertEquals(new ProductDto(2L), skuDto.getProduct());
        assertEquals(List.of(new SkuMaterialDto(3L)), skuDto.getMaterials());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), skuDto.getQuantity());
        assertEquals(1, skuDto.getVersion());
    }

    @Test
    public void testGetSetId() {
        skuDto.setId(1L);
        assertEquals(1L, skuDto.getId());
    }

    @Test
    public void testGetSetName() {
        skuDto.setName("testName");
        assertEquals("testName", skuDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        skuDto.setDescription("testDescription");
        assertEquals("testDescription", skuDto.getDescription());
    }

    @Test
    public void testGetSetProduct() {
        skuDto.setProduct(new ProductDto(2L));
        assertEquals(new ProductDto(2L), skuDto.getProduct());
    }

    @Test
    public void testGetSetMaterials() {
        skuDto.setMaterials(List.of(new SkuMaterialDto(3L)));
        assertEquals(List.of(new SkuMaterialDto(3L)), skuDto.getMaterials());
    }

    @Test
    public void testGetSetQuantity() {
        skuDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), skuDto.getQuantity());
    }

    @Test
    public void testGetSetVersion() {
        skuDto.setVersion(1);
        assertEquals(1, skuDto.getVersion());
    }
}
