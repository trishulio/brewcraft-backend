package io.company.brewcraft.dto;

import java.util.Set;

import io.company.brewcraft.pojo.Category;

public interface BaseCategory {

    public String getName();
    
    public void setName(String name);
    
    public Category getParentCategory();

    public void setParentCategory(Category parentCategory);

    public Set<Category> getSubcategories();

    public void setSubcategories(Set<Category> subcategories);
    
}
