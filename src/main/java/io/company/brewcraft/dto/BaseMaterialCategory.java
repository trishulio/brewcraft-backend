package io.company.brewcraft.dto;

import java.util.Set;

import io.company.brewcraft.pojo.MaterialCategory;

public interface BaseMaterialCategory {

    public String getName();
    
    public void setName(String name);
    
    public MaterialCategory getParentCategory();

    public void setParentCategory(MaterialCategory parentCategory);

    public Set<MaterialCategory> getSubcategories();

    public void setSubcategories(Set<MaterialCategory> subcategories);
    
}
