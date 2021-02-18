package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateMaterialCategoryDtoTest {

    private UpdateMaterialCategoryDto updateMaterialCategoryDto;

    @BeforeEach
    public void init() {
        updateMaterialCategoryDto = new UpdateMaterialCategoryDto();
    }
    
    @Test
    public void testConstructor() {
        Long parentCategoryId = 2L;
        String name = "testName";
        int version = 1;

        UpdateMaterialCategoryDto updateMaterialCategoryDto = new UpdateMaterialCategoryDto(parentCategoryId, name, version);
        
        assertSame(parentCategoryId, updateMaterialCategoryDto.getParentCategoryId());
        assertSame(name, updateMaterialCategoryDto.getName());
        assertSame(version, updateMaterialCategoryDto.getVersion());        
    }
    
    @Test
    public void testGetSetParentCategoryId() {
        Long parentCategoryId = 1L;
        updateMaterialCategoryDto.setParentCategoryId(parentCategoryId);
        assertSame(parentCategoryId, updateMaterialCategoryDto.getParentCategoryId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        updateMaterialCategoryDto.setName(name);
        assertSame(name, updateMaterialCategoryDto.getName());
    }
  
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateMaterialCategoryDto.setVersion(version);
        assertSame(version, updateMaterialCategoryDto.getVersion());
    }
}
