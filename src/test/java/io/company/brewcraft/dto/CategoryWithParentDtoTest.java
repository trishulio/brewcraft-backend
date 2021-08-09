package io.company.brewcraft.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryWithParentDtoTest {

    private CategoryWithParentDto category;

    @BeforeEach
    public void init() {
        category = new CategoryWithParentDto();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        CategoryDto parentCategory = new CategoryDto(2L, null, "parentName", null);
        String name = "testName";
        int version = 1;

        CategoryWithParentDto category = new CategoryWithParentDto(id, parentCategory, name, version);

        assertEquals(1L, category.getId());
        assertEquals(new CategoryDto(2L, null, "parentName", null), category.getParentCategory());
        assertEquals("testName", category.getName());
        assertEquals(1, category.getVersion());
    }

    @Test
    public void testGetSetId() {
        category.setId(1L);
        assertEquals(1L, category.getId());
    }

    @Test
    public void testGetSetParentCategory() {
        CategoryDto parentCategory = new CategoryDto(2L, null, "parentName", null);
        category.setParentCategory(parentCategory);
        assertEquals(new CategoryDto(2L, null, "parentName", null), category.getParentCategory());
    }

    @Test
    public void testGetSetName() {
        category.setName("testName");
        assertEquals("testName", category.getName());
    }

    @Test
    public void testGetSetVersion() {
        category.setVersion(1);
        assertEquals(1, category.getVersion());
    }
}
