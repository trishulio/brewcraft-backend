package io.company.brewcraft.dto;

import java.util.List;

import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.ProductMeasureValue;

public interface BaseProduct {
    
    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public ProductCategory getCategory();

    public void setCategory(ProductCategory category);

    public List<ProductMeasureValue> getTargetMeasures();

    public void setTargetMeasures(List<ProductMeasureValue> targetMeasures);
    
}
