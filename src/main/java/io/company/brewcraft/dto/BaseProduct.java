package io.company.brewcraft.dto;

import io.company.brewcraft.model.ProductCategory;
import io.company.brewcraft.model.ProductMeasures;

public interface BaseProduct {
    
    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public ProductCategory getCategory();

    public void setCategory(ProductCategory category);

    public ProductMeasures getTargetMeasures();

    public void setTargetMeasures(ProductMeasures targetMeasures);
    
}
