package io.company.brewcraft.dto;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.Product;

public interface BaseProductMeasureValue {

    public Product getProduct();

    public void setProduct(Product product);

    public Measure getProductMeasure();

    public void setProductMeasure(Measure productMeasure);

    public String getValue();

    public void setValue(String value);

}
