package io.company.brewcraft.dto;

import io.company.brewcraft.pojo.Category;
import io.company.brewcraft.pojo.ProductMeasures;

public interface BaseProduct {
    
    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public Category getCategory();

    public void setCategory(Category category);

    public ProductMeasures getTargetMeasures();

    public void setTargetMeasures(ProductMeasures targetMeasures);
    
}
