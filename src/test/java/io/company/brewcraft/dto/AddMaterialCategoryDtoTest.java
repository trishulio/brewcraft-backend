package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddMaterialCategoryDtoTest {

    private AddMaterialCategoryDto addMaterialCategoryDto;

    @BeforeEach
    public void init() {
        addMaterialCategoryDto = new AddMaterialCategoryDto();
    }
    
    @Test
    public void testConstructor() {
        Long parentCategoryId = 2L;
        String name = "testName";

        AddMaterialCategoryDto materialCategory = new AddMaterialCategoryDto(parentCategoryId, name);
        
        assertSame(parentCategoryId, materialCategory.getParentCategoryId());
        assertSame(name, materialCategory.getName());
    }
    
    @Test
    public void testGetSetParentCategoryId() {
        Long parentCategoryId = 1L;
        addMaterialCategoryDto.setParentCategoryId(parentCategoryId);
        assertSame(parentCategoryId, addMaterialCategoryDto.getParentCategoryId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        addMaterialCategoryDto.setName(name);
        assertSame(name, addMaterialCategoryDto.getName());
    }

}
