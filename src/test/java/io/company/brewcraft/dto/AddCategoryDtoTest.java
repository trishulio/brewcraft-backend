package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AddCategoryDtoTest {

    private AddCategoryDto addCategoryDto;

    @BeforeEach
    public void init() {
        addCategoryDto = new AddCategoryDto();
    }

    @Test
    public void testConstructor() {
        Long parentCategoryId = 2L;
        String name = "testName";

        AddCategoryDto category = new AddCategoryDto(parentCategoryId, name);

        assertSame(parentCategoryId, category.getParentCategoryId());
        assertSame(name, category.getName());
    }

    @Test
    public void testGetSetParentCategoryId() {
        Long parentCategoryId = 1L;
        addCategoryDto.setParentCategoryId(parentCategoryId);
        assertSame(parentCategoryId, addCategoryDto.getParentCategoryId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        addCategoryDto.setName(name);
        assertSame(name, addCategoryDto.getName());
    }

}
