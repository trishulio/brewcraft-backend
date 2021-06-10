package io.company.brewcraft.service;

import io.company.brewcraft.model.ProductMeasure;

public interface ProductMeasureAccessor {
    final String ATTR_PRODUCT_MEASURE = "productMeasure";

    ProductMeasure getProductMeasure();
    
    void setProductMeasure(ProductMeasure productMeasure);
}
