package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateSkuDtoTest {

    private UpdateSkuDto updateSkuDto;

    @BeforeEach
    public void init() {
        updateSkuDto = new UpdateSkuDto();
    }

    @Test
    public void testConstructor() {
        String name = "testName";
        String description = "testDescription";
        Long productId = 2L;
        List<UpdateSkuMaterialDto> materials = List.of(new UpdateSkuMaterialDto(3L));
        QuantityDto quantity = new QuantityDto("hl", BigDecimal.valueOf(100.0));
        Integer version = 1;

        UpdateSkuDto updateSkuDto = new UpdateSkuDto(name, description, productId, materials, quantity, version);

        assertEquals("testName", updateSkuDto.getName());
        assertEquals("testDescription", updateSkuDto.getDescription());
        assertEquals(2L, updateSkuDto.getProductId());
        assertEquals(List.of(new UpdateSkuMaterialDto(3L)), updateSkuDto.getMaterials());
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), updateSkuDto.getQuantity());
        assertEquals(1, updateSkuDto.getVersion());
    }

    @Test
    public void testGetSetName() {
        updateSkuDto.setName("testName");
        assertEquals("testName", updateSkuDto.getName());
    }

    @Test
    public void testGetSetDescription() {
        updateSkuDto.setDescription("testDescription");
        assertEquals("testDescription", updateSkuDto.getDescription());
    }

    @Test
    public void testGetSetProductId() {
        updateSkuDto.setProductId(2L);
        assertEquals(2L, updateSkuDto.getProductId());
    }

    @Test
    public void testGetSetMaterials() {
        updateSkuDto.setMaterials(List.of(new UpdateSkuMaterialDto(3L)));
        assertEquals(List.of(new UpdateSkuMaterialDto(3L)), updateSkuDto.getMaterials());
    }

    @Test
    public void testGetSetQuantity() {
        updateSkuDto.setQuantity(new QuantityDto("hl", BigDecimal.valueOf(100.0)));
        assertEquals(new QuantityDto("hl", BigDecimal.valueOf(100.0)), updateSkuDto.getQuantity());
    }

    @Test
    public void testGetSetVersion() {
        updateSkuDto.setVersion(1);
        assertEquals(1, updateSkuDto.getVersion());
    }
}
