package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.ProductMeasureValue;

public interface ProductMeasureValueService {
        
    public List<ProductMeasureValue> merge(List<ProductMeasureValue> existingValues, List<ProductMeasureValue> newValues);
}
