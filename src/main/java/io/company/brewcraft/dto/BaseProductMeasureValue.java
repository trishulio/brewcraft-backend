package io.company.brewcraft.dto;

import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.ProductMeasure;

public interface BaseProductMeasureValue {
    
    public Product getProduct();

    public void setProduct(Product product);

    public ProductMeasure getProductMeasure();

    public void setProductMeasure(ProductMeasure productMeasure);

    public String getValue();

    public void setValue(String value);
    
}
