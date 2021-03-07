package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateCategoryDtoTest {

    private UpdateCategoryDto updateCategoryDto;

    @BeforeEach
    public void init() {
        updateCategoryDto = new UpdateCategoryDto();
    }
    
    @Test
    public void testConstructor() {
        Long parentCategoryId = 2L;
        String name = "testName";
        int version = 1;

        UpdateCategoryDto updateCategoryDto = new UpdateCategoryDto(parentCategoryId, name, version);
        
        assertSame(parentCategoryId, updateCategoryDto.getParentCategoryId());
        assertSame(name, updateCategoryDto.getName());
        assertSame(version, updateCategoryDto.getVersion());        
    }
    
    @Test
    public void testGetSetParentCategoryId() {
        Long parentCategoryId = 1L;
        updateCategoryDto.setParentCategoryId(parentCategoryId);
        assertSame(parentCategoryId, updateCategoryDto.getParentCategoryId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        updateCategoryDto.setName(name);
        assertSame(name, updateCategoryDto.getName());
    }
  
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        updateCategoryDto.setVersion(version);
        assertSame(version, updateCategoryDto.getVersion());
    }
}
