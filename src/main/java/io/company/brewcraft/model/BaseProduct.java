package io.company.brewcraft.model;

import java.util.List;

public interface BaseProduct {
    final String ATTR_NAME = "name";
    final String ATTR_DESCRIPTION = "description";
    final String ATTR_CATEGORY = "category";
    final String ATTR_TARGET_MEASURES = "targetMeasures";

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    ProductCategory getCategory();

    void setCategory(ProductCategory category);

    List<ProductMeasureValue> getTargetMeasures();

    void setTargetMeasures(List<ProductMeasureValue> trgtMeasures);

    void addTargetMeasure(ProductMeasureValue productMeasureValue);
}
