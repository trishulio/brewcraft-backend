package io.company.brewcraft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateProductMeasureValue;
import io.company.brewcraft.model.ProductMeasureValue;
import io.company.brewcraft.repository.ProductMeasureValueRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.ProductMeasureValueService;

@Transactional
public class ProductMeasureValueServiceImpl extends BaseService implements ProductMeasureValueService {
        
    private ProductMeasureValueRepository productMeasureValueRepository;
    
    public ProductMeasureValueServiceImpl(ProductMeasureValueRepository productMeasureValueRepository) {
        this.productMeasureValueRepository = productMeasureValueRepository;        
    }
    
    @Override
    public List<ProductMeasureValue> merge(List<ProductMeasureValue> existingValues, List<ProductMeasureValue> newValues) {
        if (existingValues == null) {
            return newValues;
        }
        
        List<ProductMeasureValue> updatedProductMeasureValues = new ArrayList<>();
        
        Map<String, ProductMeasureValue> existingProductMeasuresMap = new HashMap<String, ProductMeasureValue>();
        existingValues.forEach(measure -> existingProductMeasuresMap.put(measure.getProductMeasure().getName(), measure));
                 
        if (newValues != null) {
            newValues.forEach(measureValue -> {
                ProductMeasureValue exisingValue = existingProductMeasuresMap.get(measureValue.getProductMeasure().getName());
                if (exisingValue != null) {
                    exisingValue.override(measureValue, getPropertyNames(UpdateProductMeasureValue.class));
                    updatedProductMeasureValues.add(exisingValue);
                } else {
                    updatedProductMeasureValues.add(measureValue);
                }
            });
        }
        
        return updatedProductMeasureValues;
    }
        
}
