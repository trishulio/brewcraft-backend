package io.company.brewcraft.dto;

import java.util.Set;

import io.company.brewcraft.model.MaterialCategory;


public interface BaseMaterialCategory<T extends BaseMaterialCategory<T>> {

    public String getName();
    
    public void setName(String name);
    
    public MaterialCategory getParentCategory();

    public void setParentCategory(MaterialCategory parentCategory);

    public Set<T> getSubcategories();

    public void setSubcategories(Set<T> subcategories);
    
}
